package am.home.chat.services.impl;

import am.home.chat.dao.UsersDao;
import am.home.chat.dao.sql.UsersDaoSql;
import am.home.chat.exceptions.DatabaseException;
import am.home.chat.exceptions.FileUploadException;
import am.home.chat.models.User;
import am.home.chat.services.UsersService;
import am.home.chat.utils.db.ConnectionFactory;

import java.io.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UsersServiceImpl implements UsersService {
    private UsersDao usersDao;

    public UsersServiceImpl(){
        this.usersDao = new UsersDaoSql();
    }

    @Override
    public User add(User user, InputStream imageContent) throws DatabaseException, FileUploadException {
        String imageName = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();
        String path = UsersServiceImpl.class.getClassLoader().getResource("../../images").getFile() + imageName;

        try(Connection connection = ConnectionFactory.getInstance().getConnection(false)) {
            if(imageContent != null){
                try(OutputStream outputStream = new FileOutputStream(path)) {

                    byte[] buffer = new byte[2048];
                    int readCount;

                    while ((readCount = imageContent.read(buffer)) > -1){
                        outputStream.write(buffer, 0, readCount);
                    }
                    user.setImageUrl("/images/" + imageName);

                } catch (IOException e) {
                    throw new FileUploadException(e);
                }
            } else {
                user.setImageUrl("/images/incognito.png");
            }

            try {
               user = this.usersDao.insert(connection, user);
               connection.commit();
            } catch (SQLException e){
                connection.rollback();
                throw e;
            }

            return user;

        } catch (SQLException e) {
            if(user.getId() > 0){
                new File(path).delete();
            }
            throw new DatabaseException(e);
        }
    }

    @Override
    public Optional<User> get(int id) throws DatabaseException {
        try {
            return this.usersDao.fetch(id);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public Optional<User> get(String email, String password) throws DatabaseException {
        try {
            return this.usersDao.fetch(email, password);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public boolean userExist(String email) throws DatabaseException {
        try {
            return this.usersDao.userExist(email);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public List<User> getAllUsers() throws DatabaseException {
        try {
            return this.usersDao.fetchAll();
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
