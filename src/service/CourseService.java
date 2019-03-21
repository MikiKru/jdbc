package service;

import configuration.DBConnector;

import java.sql.Connection;
import java.sql.SQLException;

public class CourseService {
    private Connection connection;

    public CourseService() throws SQLException {
        DBConnector db = new DBConnector();
        connection = db.initConnection();
    }
    public int getAllCourses(){
        
        return
    }
    public int getMyCourses(int id){

        return
    }

}
