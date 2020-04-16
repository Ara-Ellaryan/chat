package am.home.chat.servlets.users;

import am.home.chat.exceptions.DatabaseException;
import am.home.chat.models.User;
import am.home.chat.utils.JsonUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Optional;

@WebServlet("/userSearch")
public class UserSearchServlet extends BaseUserServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");

        try {
            Optional<User> userOptional = this.usersService.get(email);
            String userInfo;

            if (!userOptional.isPresent()){
                userInfo =  "User whit searching email doesn`t exist!";
            } else {
                User user = userOptional.get();
                userInfo = JsonUtil.serialize(user);
            }

            resp.setContentType("application/json");
            resp.setStatus(200);
            try(Writer writer = resp.getWriter()) {
                writer.write(userInfo);
            }

        } catch (DatabaseException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
