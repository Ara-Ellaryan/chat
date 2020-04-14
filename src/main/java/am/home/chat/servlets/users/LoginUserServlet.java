package am.home.chat.servlets.users;

import am.home.chat.exceptions.DatabaseException;
import am.home.chat.models.User;
import am.home.chat.servlets.validator.RequestValidator;
import am.home.chat.utils.DataValidator;
import am.home.chat.utils.EncryptionUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@WebServlet("/login")
public class LoginUserServlet extends BaseUserServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestValidator<User> validator = validate(req);

        try {
            if (!validator.hasError()) {
                User user = validator.getValue();
                Optional<User> optionalUser = this.usersService.get(user.getEmail(), user.getPassword());
                if (!optionalUser.isPresent()){
                    req.setAttribute("wrongEmailPassword", "Wrong email or password!");
                } else  {
                    user = optionalUser.get();
                    req.getSession().setAttribute("user", user);
                    resp.sendRedirect("home");
                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("globalError", "");
        }

        req.getRequestDispatcher("WEB-INF/pages/login.jsp").forward(req, resp);
    }

    private RequestValidator<User> validate(HttpServletRequest request) {
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        boolean hasError = false;

        if (DataValidator.isNullOrBlank(email)) {
            request.setAttribute("errorEmail", "Email is required!");
            hasError = true;
        } else if (!DataValidator.isValidEmail(email)) {
            request.setAttribute("errorEmail", "Wrong email format!");
            hasError = true;
        }
        if (DataValidator.isNullOrBlank(password)) {
            request.setAttribute("errorPassword", "Password is required!");
            hasError = true;
        }

        RequestValidator<User> validator = new RequestValidator<>();
        if (!hasError) {
            User user = new User();
            user.setEmail(email);
            user.setPassword(EncryptionUtil.encrypt(password));

            validator.setValue(user);
        } else {
            validator.setHasError(true);
        }

        return validator;
    }

}
