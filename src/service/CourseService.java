package service;

import configuration.DBConnector;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class CourseService {
    private Connection connection;

    public CourseService() throws SQLException {
        DBConnector db = new DBConnector();
        connection = db.initConnection();
    }
    public int getAllCourses() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(
                "select count(*) from courses");
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        resultSet.close();
        stmt.close();
        return 0;
    }
    public int getMyCourses(int id) throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet resultSet = stmt.executeQuery(
                "select count(*) from submission where id_u = "+id);
        if(resultSet.next()){
            return resultSet.getInt(1);
        }
        resultSet.close();
        stmt.close();
        return 0;
    }

}
