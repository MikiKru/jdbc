package controller;

import configuration.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField pf_password;

    @FXML
    void loginAction(ActionEvent event) {

    }

    @FXML
    void registerAction(ActionEvent event) {

    }

    public void initialize() throws SQLException {
        DBConnector dbConnector = new DBConnector();
        Connection connection = dbConnector.initConnection();
    }

}
