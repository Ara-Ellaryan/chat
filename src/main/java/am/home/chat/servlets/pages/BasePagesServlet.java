package am.home.chat.servlets.pages;

import am.home.chat.services.MessagesService;
import am.home.chat.services.UsersService;
import am.home.chat.services.impl.MessagesServiceImpl;
import am.home.chat.services.impl.UsersServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BasePagesServlet extends HttpServlet {
    protected UsersService usersService;
    protected MessagesService messagesService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.messagesService = new MessagesServiceImpl();
        this.usersService = new UsersServiceImpl();
    }
}
