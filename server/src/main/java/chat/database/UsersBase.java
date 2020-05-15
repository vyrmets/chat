package chat.database;

import chat.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersBase {

    private static final Logger LOGGER = Logger.getLogger(UsersBase.class);
    private static final String URL = "jdbc:mysql://localhost:3306/Users?serverTimezone=UTC";
    private static final String NAME_BASE = "root";
    private static final String PASS_BASE = "root123321";
    private Connection connection;
    private PreparedStatement preparedStatement;
    private static final String SQL_INSERT = "INSERT INTO user (name, password) VALUES (?,?)";
    private static final String SQL_SHOW = "SELECT * FROM user";

    public UsersBase() throws SQLException, ClassNotFoundException {
        this.connection = connectionBase();
    }

    public void addToDataBase(String name, String password) throws SQLException {
        preparedStatement = preparedStatement(connection, SQL_INSERT);
        preparedStatement.setString(1, name);
        preparedStatement.setString(2, password);
        preparedStatement.executeUpdate();
        closeConnectionBase();
    }

    public List<User> dataBase() throws SQLException {
        List<User> list = new ArrayList<>();
        preparedStatement = preparedStatement(connection, SQL_SHOW);
        ResultSet resultSet = displayBase();

        while (resultSet.next()) {
            User user = new User();
            user.setName(resultSet.getString("name"));
            user.setPassword(resultSet.getString("password"));
            list.add(user);
        }
        closeConnectionBase();
        resultSet.close();
        return list;
    }

    private Connection connectionBase() throws SQLException {
        LOGGER.info("Registering JDBC driver...");
        Connection connectionDB = DriverManager.getConnection(URL, NAME_BASE, PASS_BASE);
        LOGGER.info("Database connected!");
        return connectionDB;
    }

    private PreparedStatement preparedStatement(Connection connection, String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    private ResultSet displayBase() throws SQLException {
        return preparedStatement.executeQuery();
    }

    private void closeConnectionBase() throws SQLException {
        preparedStatement.close();
        connection.close();
        LOGGER.info("Connection with DB close");
    }
}
