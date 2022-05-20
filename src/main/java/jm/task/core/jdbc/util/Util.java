package jm.task.core.jdbc.util;

import java.io.Closeable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Util {
    // реализуйте настройку соеденения с БД
    private static final String URL = "jdbc:mysql://localhost:3306/test";
    private static final String USR = "adm";
    private static final String PASS = "1234";
    private Util(){
    }

    public static Connection letsConnect(){
        try {
            return DriverManager.getConnection(URL, USR, PASS);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static Statement letsStatm(Connection conn){
        try {
            return conn.createStatement();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static void clouse(AutoCloseable... obj){
       for (AutoCloseable item : obj) {
           if (item != null) {
               try {
                   item.close();
               } catch (Exception ignored) {
               }
           }
       }

    }
}