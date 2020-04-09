package am.home.chat.dao.sql;

import am.home.chat.dao.MessagesDao;
import am.home.chat.models.Message;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class MessagesDaoSql extends BaseSQL implements MessagesDao {

    @Override
    public Message insert(Message message) throws SQLException {
        String query = "INSERT INTO messages(sender_id, receiver_id, message) VALUES (?, ?, ?);";

        try (Connection connection = this.connectionFactory.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            preparedStatement.setInt(1, message.getSenderId());
            preparedStatement.setInt(2, message.getReceiverId());
            preparedStatement.setString(3, message.getMessage());
            preparedStatement.execute();

            try (ResultSet resultSet = preparedStatement.getGeneratedKeys()) {
                if (resultSet.next()) {
                    message.setId(resultSet.getInt(1));
                }
            }
        }

        return message;
    }

    @Override
    public List<Message> fetchAll(int senderId, int receiverId) throws SQLException {
        String query = "SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?)" +
                " OR (sender_id = ? AND receiver_id = ?) order by created_at;";
        List<Message> messagesList = new LinkedList<>();

        try(Connection connection = this.connectionFactory.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setInt(1, senderId);
            preparedStatement.setInt(2, receiverId);
            preparedStatement.setInt(3, receiverId);
            preparedStatement.setInt(4, senderId);

            try(ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()){
                    Message message = new Message();

                    message.setId(resultSet.getInt("id"));
                    message.setSenderId(resultSet.getInt("sender_id"));
                    message.setReceiverId(resultSet.getInt("receiver_id"));
                    message.setMessage(resultSet.getString("message"));
                    message.setCreatedAt(resultSet.getTimestamp("created_at"));

                    messagesList.add(message);
                }
            }
        }

        return messagesList;
    }
}
