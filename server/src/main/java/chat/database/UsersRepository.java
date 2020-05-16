package chat.database;

import chat.model.User;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersRepository {

    private static final Logger LOGGER = Logger.getLogger(UsersRepository.class);
    private static final String URL = "jdbc:mysql://localhost:3306/Users?serverTimezone=UTC";
    private static final String NAME_BASE = "root";
    private static final String PASS_BASE = "root123321";
    private static final String SQL_INSERT = "INSERT INTO user (name, password) VALUES (?,?)";
    private static final String SQL_SHOW = "SELECT * FROM user";

    public UsersRepository() {
    }

    public void saveUser(User user) {
        try (Connection connection = connectionBase(); PreparedStatement preparedStatement = preparedStatement(connection, SQL_INSERT)) {
            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LOGGER.info("Failed to save user in Database ", e);
        }
    }

    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        try (Connection connection = connectionBase(); PreparedStatement preparedStatement = preparedStatement(connection, SQL_SHOW); ResultSet resultSet = displayBase(connection,preparedStatement)) {
            while (resultSet.next()) {
                User user = new User();
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
                list.add(user);
            }
        } catch (SQLException e) {
            LOGGER.info("Failed to save user in Database ", e);
        }
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

    private ResultSet displayBase(Connection connection, PreparedStatement preparedStatement) throws SQLException {
            return preparedStatement.executeQuery();
    }
}
