package am.home.chat.dao.sql;

import am.home.chat.dao.UsersDao;
import am.home.chat.models.User;
import am.home.chat.utils.db.DataSource;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UsersDaoSql implements UsersDao {

    @Override
    public User insert(User user) throws SQLException {
        String query = "INSERT INTO users(name, surname, email, password, image_url) VALUES (?, ?, ?, ? , ?);";

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setString(1, user.getName());
            preparedStatement.setString(2, user.getSurname());
            preparedStatement.setString(3, user.getEmail());
            preparedStatement.setString(4, user.getPassword());
            preparedStatement.setString(5, user.getImageUrl());
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    user.setId(resultSet.getInt(1));
                }
            }
        }

        return user;
    }

    @Override
    public Optional<User> fetch(int id) throws SQLException {
        String query = "SELECT * FROM users WHERE id = ?;";
        User user = null;
        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createFromResult(resultSet);
                }
            }
        }
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public Optional<User> fetch(String email, String password) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ? AND password = ?;";
        User user = null;

        try (Connection connection = DataSource.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    user = createFromResult(resultSet);
                }
            }
        }

        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public Optional<User> fetch(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?;";
        User user = null;

        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);

            try(ResultSet resultSet = statement.executeQuery()) {
                if(resultSet.next()){
                    user = createFromResult(resultSet);
                }
            }
        }
        return user == null ? Optional.empty() : Optional.of(user);
    }

    @Override
    public boolean userExist(String email) throws SQLException {
        String query = "SELECT * FROM users WHERE email = ?;";

        try(Connection connection = DataSource.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, email);
            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<User> fetchAll() throws SQLException {
        String query = "SELECT * FROM users";
        List<User> usersList = new LinkedList<>();

        try(Connection connection = DataSource.getConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(query)) {
            while (resultSet.next()){
                usersList.add(createFromResult(resultSet));
            }
        }

        return usersList;
    }

    @Override
    public List<User> fetchAllContacts(int id) throws SQLException {
        String query = "SELECT DISTINCT t.* FROM (SELECT * FROM users WHERE users.id != ?) AS t INNER JOIN " +
                "(SELECT * FROM messages ORDER BY messages.created_at DESC ) as m ON " +
                "((m.sender_id = ? and m.receiver_id = t.id) or (m.sender_id = t.id and m.receiver_id = ?));";

        List<User> contactList = new LinkedList<>();

        try(Connection connection = DataSource.getConnection();
        PreparedStatement statement = connection.prepareStatement(query)){
            statement.setInt(1, id);
            statement.setInt(2, id);
            statement.setInt(3, id);

            try(ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()){
                    User user = createFromResult(resultSet);
                    contactList.add(user);
                }
            }
        }
        return contactList;
    }

    private User createFromResult(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setSurname(resultSet.getString("surname"));
        user.setEmail(resultSet.getString("email"));
//        user.setPassword(resultSet.getString("password"));
        user.setImageUrl(resultSet.getString("image_url"));

        return user;
    }
}
