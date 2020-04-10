package chat.database;

import chat.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersBase {

    // private User user;

    private static final Logger LOGGER = Logger.getLogger(UsersBase.class);
    private static final String URL = "jdbc:mysql://localhost:3306/Users?serverTimezone=UTC";
    private static final String NAME_BASE = "root";
    private static final String PASS_BASE = "root123321";
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;
    // private String SQL_INSERT = "INSERT INTO user VALUES" + "('" + user.getName() + "','" + user.getPassword() + "')";
    private String SQL_SHOW = "SELECT * FROM user";

    public UsersBase() throws SQLException, ClassNotFoundException {
        this.connection = connectionBase();
        this.statement = statement(connection);
        this.resultSet = displayBase(SQL_SHOW);
    }


    public List<User> dataBase() throws SQLException {
        List<User> list = new ArrayList<>();
        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            list.add(user);
        }
        return list;
    }

    private Connection connectionBase() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.jdbc.Driver");
        LOGGER.info("Registering JDBC driver...");
        Connection connection = DriverManager.getConnection(URL, NAME_BASE, PASS_BASE);
        LOGGER.info("Database connected!");
        return connection;
    }

    private Statement statement(Connection connection) throws SQLException {
        return connection.createStatement();
    }

    private ResultSet displayBase(String sql) throws SQLException {
        return statement.executeQuery(sql);
    }

    private void closeConnectionBase() throws SQLException {
        resultSet.close();
        statement.close();
        connection.close();
    }
}
