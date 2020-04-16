package am.home.chat.servlets.pages;

import am.home.chat.models.Message;
import am.home.chat.models.User;
import am.home.chat.servlets.users.BaseUserServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/home", ""})
public class HomeServlet extends BasePagesServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            User user = (User)req.getSession().getAttribute("user");
            List<User> users = this.usersService.getAllUsers();
            List<Message> messages = this.messagesService.getAllMessages(user.getId(), user.getId());

            req.setAttribute("users", users);
            req.setAttribute("messages", messages);
            req.getRequestDispatcher("WEB-INF/pages/home.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
