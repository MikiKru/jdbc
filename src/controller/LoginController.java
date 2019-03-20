package controller;

import configuration.DBConnector;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

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

    @FXML
    void loginAction(ActionEvent event) throws SQLException {
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
            // jeżeli jest niepusta to wyciągam zawartość
            System.out.println(resultSet.getInt(1));
            System.out.println(resultSet.getString(2));
            System.out.println(resultSet.getString(3));
            System.out.println(resultSet.getString(4));
            System.out.println(resultSet.getString(5));
            System.out.println(resultSet.getBoolean(6));
            System.out.println(resultSet.getString(7));
            System.out.println(resultSet.getDate(8));
        } else {
            System.out.println("Błąd logowania!");
        }
    }

    @FXML
    void registerAction(ActionEvent event) throws IOException {
        // utworzenie okna nowego widoku
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("/view/registerView.fxml"));
        stage.setTitle("Panel rejestracji");
        stage.setScene(new Scene(root));
        stage.show();
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
