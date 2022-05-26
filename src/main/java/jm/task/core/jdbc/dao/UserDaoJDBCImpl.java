package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private Connection conn;
    private Statement statement;
    private PreparedStatement pstatment;



    public void createUsersTable() {

        conn = Util.getConnect();
        statement = Util.getStatement(conn);
        try {
            statement.execute("CREATE TABLE IF NOT EXISTS User (" +
                    "id BIGINT unsigned not null auto_increment  PRIMARY KEY," +
                    "name varchar(25) not null," +
                    "lastName varchar(25) not null," +
                    "age TINYINT not null )");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.connectClose(statement, conn);
        }

    }

    public void dropUsersTable() {
        conn = Util.getConnect();
        statement = Util.getStatement(conn);

        try {
            statement.execute("drop table if exists User");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.connectClose(statement, conn);
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        try {
            conn = Util.getConnect();
            pstatment = conn.prepareStatement("INSERT INTO user (name, lastName, age) VALUES (?,?,?)");
            pstatment.setString(1, name);
            pstatment.setString(2, lastName);
            pstatment.setByte(3, age);
            pstatment.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.connectClose(pstatment, conn);
        }
    }

    public void removeUserById(long id) {
        try {
            conn = Util.getConnect();
            pstatment = conn.prepareStatement("delete from user where id = ?");
            pstatment.setLong(1, id);
            pstatment.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.connectClose(pstatment, conn);
        }
    }

    public List<User> getAllUsers() {

        List<User> myUsers = new ArrayList<>();
        User tmpUsr;
        conn = Util.getConnect();
        statement = Util.getStatement(conn);
        ResultSet rs= null;

        try {
            rs = statement.executeQuery("select * from user");

            while (rs.next()){
                tmpUsr =new User(rs.getString("name"),
                        rs.getString("lastName"),
                        rs.getByte("age"));
                tmpUsr.setId(rs.getLong("id"));
                myUsers.add(tmpUsr);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
             Util.connectClose(statement, conn, rs);
        }
        return myUsers;
    }

    public void cleanUsersTable() {
        conn = Util.getConnect();
        statement = Util.getStatement(conn);

        try {
            statement.execute("TRUNCATE TABLE user");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            Util.connectClose(statement, conn);
        }
    }

}
