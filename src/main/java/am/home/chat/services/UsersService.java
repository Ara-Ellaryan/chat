package am.home.chat.services;

import am.home.chat.exceptions.DatabaseException;
import am.home.chat.exceptions.FileUploadException;
import am.home.chat.models.User;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

public interface UsersService {

    User add(User user, InputStream imageContent) throws DatabaseException, FileUploadException;

    Optional<User> get(int id) throws DatabaseException;

    Optional<User> get(String email, String password) throws DatabaseException;

    Optional<User> get(String email) throws DatabaseException;

    boolean userExist(String email) throws DatabaseException;

    List<User> getAllUsers() throws DatabaseException;
}
