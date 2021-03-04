package controlers;

import util.ConnectionUtil;
import util.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

public class Login {

    @FXML
    private Label lblError;
    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnSignUp;
    Connection con = null;
    Statement statement = null;
    ResultSet resultSet = null;

    @FXML
    public void handleButtonAction(MouseEvent event) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if (username.isEmpty() || password.isEmpty()) {
            showMessage(Color.TOMATO, "Please make sure fields are not left empty");
            return;
        }else {
            if(event.getSource() == btnSignUp) {
                if(signUp(username,password)) {
                    showMessage(Color.GREEN, "Account created!");
                    return;
                }else {
                    showMessage(Color.TOMATO, "Username/email is already taken");
                }
            } else {
                if(login(username,password)) {
                    showMessage(Color.GREEN, "Success");
                    return;
                }
            }
        }
    }

    private void showMessage(Color color, String message) {
        lblError.setTextFill(color);
        lblError.setText(message);
        System.out.println(message);


    }

    private boolean signUp(String username, String password) {
        String existingUsername;
        String sql = "Select * from users Where user_name = ?";
        // connect to Db and test to see if username is taken if not, add new credentials
        try {
            con = ConnectionUtil.connectToDB();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select user_name from users");
            while(resultSet.next()) {
                existingUsername = resultSet.getString("user_name");
                if(existingUsername.equals(username)) {
                    return false;
                }
            }
            statement.executeUpdate("insert into users (user_name,user_password) values ('"+username+"', '"+password+"');");
            return true;
        }catch (Exception e) {
            showMessage(Color.TOMATO, e.getMessage());
            return false;
        }


    }

    private boolean login(String username, String password) {
        String usernameDB = "";
        String passwordDB = "";
        int userId;

        try {
            con = ConnectionUtil.connectToDB();
            statement = con.createStatement();
            resultSet = statement.executeQuery("select user_id,user_name,user_password from users");

            while (resultSet.next()) {
                userId = resultSet.getInt("user_id");
                usernameDB = resultSet.getString("user_name");
                passwordDB = resultSet.getString("user_password");

                if(username.equals(usernameDB) && password.equals(passwordDB)) {
                    // we found a match and the user is loged in
                    User session = new User(userId);
                    return true;
                }
            }
            return false;
        }catch (Exception e) {
            showMessage(Color.TOMATO, e.getMessage());
            return false;
        }
    }


}
