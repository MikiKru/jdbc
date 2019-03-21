package controller;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import model.Courses;
import model.SubmissionView;
import service.CourseService;
import service.WindowService;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

public class CourseController {

    @FXML
    private MenuItem m_logout;

    @FXML
    private MenuItem m_exit;

    @FXML
    private TableView<SubmissionView> tab_course;

    @FXML
    private TableColumn<SubmissionView, String> c_name;

    @FXML
    private TableColumn<SubmissionView, String> c_lastname;

    @FXML
    private TableColumn<SubmissionView, String> c_email;

    @FXML
    private TableColumn<SubmissionView, String> c_course;

    @FXML
    private TableColumn<SubmissionView, String> c_trainer;

    @FXML
    private TableColumn<SubmissionView, LocalDate> c_date;

    @FXML
    private ComboBox<Courses> cb_save;

    @FXML
    private Button btn_save;

    @FXML
    private Button btn_delete;

    @FXML
    private ComboBox<Courses> cb_update;

    @FXML
    private Button btn_update;

    @FXML
    private Label lbl_course_count;

    @FXML
    private Label lbl_submission_count;

    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }

    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        WindowService.showWindow("/view/loginView.fxml", "Panel logowania");
        WindowService.closeWindow(lbl_course_count);
    }

    @FXML
    void saveAction(ActionEvent event) throws SQLException {
        // pobranie wartości zaznaczonej na combobox
        Courses courses = cb_save.getValue();
        // wydobywam id_c z wybranej wartości na combobox
        int id_c = courses.getId_c();
        // service do zapisu użtytkownika na kurs
        new CourseService().saveUserOnCourse(LoginController.id_logged, id_c);
        initialize();
    }

    @FXML
    void selectRowAction(MouseEvent event) {

    }
    @FXML
    void deleteAction(ActionEvent event) {

    }

    @FXML
    void updateAction(ActionEvent event) {

    }
    @FXML
    void initialize() throws SQLException {
        // przyciski zmian w zapisach są nieaktywne
        btn_delete.setDisable(true);
        btn_update.setDisable(true);
        CourseService courseService = new CourseService();
        System.out.println(LoginController.id_logged);
        // wykonanie zapytania: ile jest dostępnych kursów
        // zwaraca liczbę kursów
        int allCoursesCount = courseService.getCountAllCourses();
        // złącza napis z liczbą kursów
        String allCoursesCountLabel = "liczba dostępnych kursów: " + allCoursesCount;
        // umieszcza napis na kontrolce
        lbl_course_count.setText(allCoursesCountLabel);
        // wykonanie zapytania na ile kursów jestem zapisany
        int myCoursesCount = courseService.getMyCourses(LoginController.id_logged);
        String myCoursesCountLabel = "liczba kursów na które jesteś zapisany: " + myCoursesCount;
        lbl_submission_count.setText(myCoursesCountLabel);
        // wpisanie kursów z DB do kontrolki ComboBox
        cb_save.setItems(courseService.getAllCourses());
        cb_update.setItems(courseService.getAllCourses());
        // wypisanie rekordów z widoku do modelu
        ObservableList<SubmissionView> submissions_list = courseService.getAllSubmissions(LoginController.id_logged);
        // konfiguracja wartości przekazywanych do tabeli
        c_name.setCellValueFactory(new PropertyValueFactory<>("username"));
        c_lastname.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        c_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        c_course.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        c_trainer.setCellValueFactory(new PropertyValueFactory<>("trainer"));
        c_date.setCellValueFactory(new PropertyValueFactory<>("start_date"));
        // wprowadzenie wartości z listy
        tab_course.setItems(submissions_list);

    }
}
