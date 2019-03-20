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
            Alert a = new Alert(Alert.AlertType.INFORMATION);
            a.setTitle("Rejestracja");
            a.setHeaderText("Zarejestrowano użytkownika");
            a.setContentText("Zarejestrowano użytkownika " + tf_login.getText());
            a.show();
            // czyszczenie pól
            clear();
            // zamknięcie okna i przejście do logowania
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("/view/loginView.fxml"));
            stage.setTitle("Panel rejestracji");
            stage.setScene(new Scene(root));
            stage.show();
            // zamknięcie okna
            Stage stageClosed = (Stage) tf_login.getScene().getWindow();
            stageClosed.close();

        } catch (NullPointerException e){
            Alert a = new Alert(Alert.AlertType.ERROR);
            a.setTitle("Błąd");
            a.setHeaderText("Musisz uzupełnić wszystkie pola");
            a.setContentText("Uzupełnij wszystkie pola!");
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