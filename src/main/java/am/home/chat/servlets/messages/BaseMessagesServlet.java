package am.home.chat.servlets.messages;

import am.home.chat.services.MessagesService;
import am.home.chat.services.impl.MessagesServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public abstract class BaseMessagesServlet extends HttpServlet {
    protected MessagesService messagesService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.messagesService = new MessagesServiceImpl();
    }
}
