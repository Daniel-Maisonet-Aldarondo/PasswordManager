package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import util.ConnectionUtil;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import util.Cypher;

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
            showMessage(Color.RED, "Please make sure fields are not left empty");
            return;
        }else {
            if(event.getSource() == btnSignUp) {
                if(signUp(username,password)) {
                    showMessage(Color.GREEN, "Account created!");
                    return;
                }else {
                    showMessage(Color.RED, "Username/email is already taken");
                }
            } else {
                if(login(username,password)) {
                    showMessage(Color.GREEN, "Success");
                    // close current scene and open new scene
                    try {
                        Node node = (Node) event.getSource();
                        Stage stage = (Stage) node.getScene().getWindow();
                        stage.close();
                        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/dashboard.fxml")));
                        stage.setScene(scene);
                        stage.show();
                    }catch (Exception e) {
                        showMessage(Color.RED, e.getMessage());
                    }
                }else {
                    showMessage(Color.RED, "Invalid login credentials");
                }
            }
        }
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
                if(existingUsername.equals(Cypher.cypher(username))) {
                    return false;
                }
            }
            statement.executeUpdate("insert into users (user_name,user_password) values ('"+Cypher.cypher(username)+"', '"+Cypher.cypher(password)+"');");
            return true;
        }catch (Exception e) {
            showMessage(Color.RED, e.getMessage());
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

                if(Cypher.cypher(username).equals(usernameDB) && Cypher.cypher(password).equals(passwordDB)) {
                    Dashboard.setSessionId(userId);
                    return true;
                }
            }
            return false;
        }catch (Exception e) {
            showMessage(Color.RED, e.getMessage());
            return false;
        }
    }

    private void showMessage(Color color, String message) {
        lblError.setTextFill(color);
        lblError.setText(message);
        System.out.println(message);


    }


}
