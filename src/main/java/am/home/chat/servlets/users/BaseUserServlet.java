package am.home.chat.servlets.users;

import am.home.chat.services.MessagesService;
import am.home.chat.services.UsersService;
import am.home.chat.services.impl.MessagesServiceImpl;
import am.home.chat.services.impl.UsersServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class BaseUserServlet extends HttpServlet {
    protected UsersService usersService;

    @Override
    public void init() throws ServletException {
        super.init();
        this.usersService = new UsersServiceImpl();
    }
}
