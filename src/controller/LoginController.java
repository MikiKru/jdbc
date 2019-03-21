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
import javafx.stage.Stage;
import service.AlertService;
import service.WindowService;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML
    private TextField tf_login;

    @FXML
    private PasswordField pf_password;

    // globalny statyczny id do przekazania między widokami
    public static int id_logged;
    @FXML
    void loginAction(ActionEvent event) throws SQLException, IOException {
        // przygotowuję zapytanie
        ps = connection.prepareStatement("SELECT * FROM users WHERE email = ? AND password = ?");
        // przypisauję wartości do ?
        ps.setString(1, tf_login.getText());
        ps.setString(2, pf_password.getText());
        // wykonuje zapytanie
        // SELECT -> executeQuery()
        // INSERT, UPDATE, DELETE, CREATE, DROP -> executeUpdate()
        // wykonałem zapytanie i zwracam wynik do tablicy wielowymiarowej
        ResultSet resultSet = ps.executeQuery();
        // przesuwam wskaźnik na pierwszą pozycję i sprawdzam czy jest niepusta
        if(resultSet.next()){
            // do statycznego id przypisuję id zalogowanego użytkownika
            id_logged = resultSet.getInt(1);
            WindowService.showWindow("/view/courseView.fxml", "Formularz zapisu na kurs");
            WindowService.closeWindow(tf_login);
        } else {
            AlertService.showAlert(Alert.AlertType.INFORMATION, "Błąd logowania", "Zarejestruj się!");
        }
    }

    @FXML
    void registerAction(ActionEvent event) throws IOException {
        // utworzenie okna nowego widoku
        WindowService.showWindow("/view/registerView.fxml", "Panel rejestracji");
        WindowService.closeWindow(tf_login);
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
