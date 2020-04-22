package am.home.chat.dao;

import am.home.chat.models.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UsersDao {

    User insert(User user) throws SQLException;

    Optional<User> fetch(int id) throws SQLException;

    Optional<User> fetch(String email, String password) throws SQLException;

    Optional<User> fetch(String email) throws SQLException;

    boolean userExist(String email) throws SQLException;

    List<User> fetchAllContacts(int id) throws SQLException;

    List<User> fetchAll() throws SQLException;
}
