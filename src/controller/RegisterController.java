package controller;

import configuration.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import service.AlertService;
import service.WindowService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;

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

    private void clear(){
        tf_name.clear();
        tf_lastname.clear();
        tf_login.clear();
        pf_password.clear();
        pf_password2.clear();
    }

    @FXML
    void clearAction(ActionEvent event) {
        clear();
    }
    private void insertData() throws IOException {
        // rejestracja użytkownika na podstawie podanych pól
        try {
            // sprawdzam czy hasła są jednokowe
            if (tf_login.getText().equals("") || pf_password.getText().equals("") || tf_name.getText().equals("") || tf_lastname.getText().equals("")) {
                throw new NullPointerException();
            }
            if (!pf_password.getText().equals(pf_password2.getText())) {
                throw new InputMismatchException();
            }
            ps = connection.prepareStatement("INSERT INTO users VALUES (default, ?, ?, ?, ?, default, default, default)");
            ps.setString(1, tf_name.getText());
            ps.setString(2, tf_lastname.getText());
            ps.setString(3, tf_login.getText());
            ps.setString(4, pf_password.getText());
            ps.executeUpdate();
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Rejestracja", "Zareejstrowano użytkownika");
            // czyszczenie pól
            clear();
            // zamknięcie okna i przejście do logowania
            WindowService.showWindow("/view/loginView.fxml", "Panel logowania");
            WindowService.closeWindow(tf_login);

        } catch (NullPointerException e){
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Brak wartości", "Uzupełnij brajujące pola");
        } catch (SQLException e){
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Błędny login", "Login istnieje w bazie danych");
        } catch (InputMismatchException e){
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Różne hasła", "Hasła muszą być jednakowe");
        }
    }
    @FXML
    void keyRegisterAction(KeyEvent event) throws IOException {
//         dla entera - rejestracja
//         dla esc - clear
//        if(event.getCode() == KeyCode.ENTER) {
//            insertData();
//        } else if(event.getCode() == KeyCode.ESCAPE){
//            clear();
//        }
        Map<KeyCode, Integer> keyCodeToInteger = new HashMap<>();
        keyCodeToInteger.put(KeyCode.ENTER, 1);
        keyCodeToInteger.put(KeyCode.ESCAPE, 2);

        switch (keyCodeToInteger.get(event.getCode())){
            case 1:
                insertData();
                break;
            case 2:
                clear();
                break;
        }
    }
    @FXML
    void registerAction(ActionEvent event) throws IOException {
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