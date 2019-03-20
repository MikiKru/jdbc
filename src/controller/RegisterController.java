package controller;

import configuration.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField pf_password;

    @FXML
    private PasswordField pf_password2;

    @FXML
    private TextField tf_name;

    @FXML
    private TextField tf_lastname;

    @FXML
    void clearAction(ActionEvent event) {
        tf_name.clear();
        tf_lastname.clear();
        tf_login.clear();
        pf_password.clear();
        pf_password2.clear();
    }

    @FXML
    void keyRegisterAction(KeyEvent event) {

    }

    @FXML
    void registerAction(ActionEvent event) {

    }
    // globalne obiekty połączenia do bazy danych
    DBConnector dbConnector;
    Connection connection;
    // globalny obiekt do wykonywania zapytań
    PreparedStatement ps;
    public void initialize() throws SQLException {
        dbConnector = new DBConnector();
        connection = dbConnector.initConnection();
    }
}