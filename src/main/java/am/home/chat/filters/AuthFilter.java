package am.home.chat.filters;

import am.home.chat.models.User;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(filterName = "AuthFilter", urlPatterns = {"/", "/home", "/userList", "/messages", "/createMessage"})
public class AuthFilter extends HttpFilter {

    private final Map<Integer, Date> USER_ACTIVITY_MAP = new ConcurrentHashMap<>();

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        User user = (User)req.getSession().getAttribute("user");
        if(user == null){
            res.sendRedirect("login");
            return;
        } else {
            USER_ACTIVITY_MAP.put(user.getId(), new Date());
        }
        chain.doFilter(req, res);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        filterConfig.getServletContext().setAttribute("users_activity", USER_ACTIVITY_MAP);
    }
}
