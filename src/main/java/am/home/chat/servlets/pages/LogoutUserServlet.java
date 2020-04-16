package am.home.chat.servlets.pages;

import am.home.chat.models.User;
import am.home.chat.servlets.users.BaseUserServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

@WebServlet("/logout")
public class LogoutUserServlet extends BasePagesServlet {
    private Map<Integer, Date> userActivity;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        userActivity = (Map<Integer, Date>) req.getServletContext().getAttribute("users_activity");
        userActivity.remove(((User)req.getSession().getAttribute("user")).getId());

        req.getSession().invalidate();
        resp.sendRedirect("login");

    }
}
