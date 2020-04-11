package am.home.chat.servlets.messages;

import am.home.chat.exceptions.DatabaseException;
import am.home.chat.models.Message;
import am.home.chat.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.List;

@WebServlet("/messages")
public class MessageLoaderServlet extends BaseMessagesServlet{
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            int senderId = Integer.parseInt(req.getParameter("senderId"));
            int receiverId = Integer.parseInt(req.getParameter("receiverId"));

            List<Message> messages = super.messagesService.getAllMessages(senderId, receiverId);
            String messagesContent = JsonUtil.serialize(messages);
            resp.setContentType("application/json");
            try(Writer writer = resp.getWriter()) {
                writer.write(messagesContent);
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
