package controllers;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Callback;
import util.ConnectionUtil;
import util.Cypher;


public class Dashboard implements Initializable {

    @FXML
    private TextField txtUrl;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnAdd;

    @FXML
    private Label lblError;

    @FXML
    private TableView table;


    PreparedStatement preparedStatement;
    Connection con;
    private ObservableList<ObservableList> data;
    String SQL = "SELECT * from container WHERE user_id = '" + sessionId + "'";


    private static int sessionId;

    public Dashboard() {
        con = ConnectionUtil.connectToDB();
    }

    public static void setSessionId(int value) {
        sessionId = value;
    }

    private void createColumnList() {
        //get the data from the sql table and create matching columns
        try {
            ResultSet rs = con.createStatement().executeQuery(SQL);

            for (int i = 1; i < rs.getMetaData().getColumnCount(); i++) {
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i + 1).toUpperCase());
                col.setCellValueFactory(new Callback<CellDataFeatures<ObservableList, String>, ObservableValue<String>>() {
                    public ObservableValue<String> call(CellDataFeatures<ObservableList, String> param) {
                        return new SimpleStringProperty(param.getValue().get(j).toString());
                    }
                });

               table.getColumns().removeAll(col);
               table.getColumns().addAll(col);

                System.out.println("Column [" + i + "] ");

            }

        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());

        }
    }


    private void createRowList() {
        //add the contents of the sql table to the table
        data = FXCollections.observableArrayList();
        ResultSet rs;
        try {
            rs = con.createStatement().executeQuery(SQL);

            while (rs.next()) {

                ObservableList row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {

                    row.add(Cypher.decrypt(rs.getString(i)));
                }
                System.out.println("Row added " + row);
                data.add(row);

            }

            table.setItems(data);
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private void saveData() {
        // insert the new data gathered from the text fields into the database
        try {
            String st = "INSERT INTO container (user_id, Url, Username, Password) VALUES (?,?,?,?)";
            preparedStatement = (PreparedStatement) con.prepareStatement(st);
            preparedStatement.setString(1, String.valueOf(sessionId));
            preparedStatement.setString(2, Cypher.cypher(txtUrl.getText()));
            preparedStatement.setString(3, Cypher.cypher(txtUsername.getText()));
            preparedStatement.setString(4, Cypher.cypher(txtPassword.getText()));
            preparedStatement.executeUpdate();
            showMessage(Color.GREEN, "Success");
            createRowList();
            table.getSelectionModel().setCellSelectionEnabled(true);
            table.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
            return;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            showMessage(Color.RED, ex.getMessage());
            return;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        createColumnList();
        createRowList();
    }

    @FXML
    private void handleEvents(MouseEvent event) {
        if (txtUrl.getText().isEmpty() || txtUsername.getText().isEmpty() || txtPassword.getText().isEmpty()) {
                showMessage(Color.RED, "Please make sure that all fields are filled");
        } else {
                saveData();
        }
    }

    @FXML
    private void logout(MouseEvent event) {
        try {
            Node node = (Node) event.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/login.fxml")));
            stage.setScene(scene);
            stage.show();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showMessage(Color color, String message) {
        lblError.setTextFill(color);
        lblError.setText(message);
        System.out.println(message);
    }

}



