package am.home.chat.services;

import am.home.chat.exceptions.DatabaseException;
import am.home.chat.models.Message;

import java.util.List;

public interface MessagesService {

    Message add(Message message) throws DatabaseException;

    List<Message> getAllMessages(int senderId, int receiverId) throws DatabaseException;
}
