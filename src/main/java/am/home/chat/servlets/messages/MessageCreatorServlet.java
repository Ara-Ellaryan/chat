package am.home.chat.servlets.messages;

import am.home.chat.exceptions.DatabaseException;
import am.home.chat.models.Message;
import am.home.chat.servlets.validator.RequestValidator;
import am.home.chat.utils.DataValidator;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("createMessage")
public class MessageCreatorServlet extends BaseMessagesServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String messageText = req.getParameter("message");
        int senderId = Integer.parseInt(req.getParameter("senderId"));
        int receiverId = Integer.parseInt(req.getParameter("receiverId"));

        Message message = new Message();
        message.setMessage(messageText);
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);

        try {
            this.messagesService.add(message);
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private RequestValidator<Message> validate(HttpServletRequest req) {
        String messageText = req.getParameter("message");
        String senderId = req.getParameter("senderId");
        String receiverId = req.getParameter("receiverId");
        boolean hasError = false;

        if (DataValidator.isNullOrBlank(messageText)) {
            req.setAttribute("errorMessage", "Message is required!");
            hasError = true;
        }
        if (DataValidator.isNullOrBlank(senderId) || !DataValidator.isNumber(senderId) ||
                DataValidator.isNullOrBlank(receiverId) || !DataValidator.isNumber(receiverId)) {
            req.setAttribute("badRequest", "Bad Request!");
            hasError = true;
        }

        RequestValidator<Message> validator = new RequestValidator<>();

        if(!hasError){
            Message message = new Message();
            message.setMessage(messageText);
            message.setSenderId(Integer.parseInt(senderId));
            message.setReceiverId(Integer.parseInt(receiverId));

            validator.setValue(message);
        } else {
            validator.setHasError(true);
        }

        return validator;
    }
}
