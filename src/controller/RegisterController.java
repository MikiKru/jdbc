package controller;

import configuration.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.InputMismatchException;

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
    private void insertData() {
        // rejestracja użytkownika na podstawie podanych pól
        try {
            // sprawdzam czy hasła są jednokowe
            if(!pf_password.getText().equals(pf_password2.getText())){
                throw new InputMismatchException();
            }
            ps = connection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?, default, default, default)");
            ps.setString(1, tf_name.getText());
            ps.setString(2, tf_lastname.getText());
            ps.setString(3, tf_login.getText());
            ps.setString(4, pf_password.getText());
            ps.executeUpdate();
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Rejestracja");
            a.setHeaderText("Zarejestrowano użytkownika");
            a.setContentText("Zarejestrowano użytkownika " + tf_login.getText());
            a.show();
        } catch (SQLException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Błąd");
            a.setHeaderText("Podany login już istnije w bazie danych");
            a.setContentText("Musisz podać inny login!");
            a.show();
        } catch (InputMismatchException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Błąd");
            a.setHeaderText("Podane hasła nie są jednokowe");
            a.setContentText("Musisz podać jednakowe hasła!");
            a.show();
        }
    }
    @FXML
    void keyRegisterAction(KeyEvent event) {

    }
    @FXML
    void registerAction(ActionEvent event) {
        insertData();
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