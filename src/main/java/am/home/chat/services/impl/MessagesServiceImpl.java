package am.home.chat.services.impl;

import am.home.chat.dao.MessagesDao;
import am.home.chat.dao.sql.MessagesDaoSql;
import am.home.chat.exceptions.DatabaseException;
import am.home.chat.models.Message;
import am.home.chat.services.MessagesService;

import java.sql.SQLException;
import java.util.List;

public class MessagesServiceImpl implements MessagesService {

    private MessagesDao messagesDao;

    public MessagesServiceImpl(){
        this.messagesDao = new MessagesDaoSql();
    }

    @Override
    public Message add(Message message) throws DatabaseException {
        try {
            return this.messagesDao.insert(message);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }

    @Override
    public List<Message> getAllMessages(int senderId, int receiverId) throws DatabaseException {
        try {
            return this.messagesDao.fetchAll(senderId, receiverId);
        } catch (SQLException e) {
            throw new DatabaseException(e);
        }
    }
}
