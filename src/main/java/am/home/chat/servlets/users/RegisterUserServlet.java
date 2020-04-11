package am.home.chat.servlets.users;

import am.home.chat.models.User;
import am.home.chat.servlets.validator.RequestValidator;
import am.home.chat.utils.DataValidator;
import am.home.chat.utils.EncryptionUtil;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/register")
public class RegisterUserServlet extends BaseUserServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            RequestValidator<User> validator = validate(req);

            if(!validator.hasError()){
                User user = validator.getValue();
                boolean userExist = this.usersService.userExist(user.getEmail());

                if(!userExist){
                    InputStream imageStream = DataValidator.isEmpty(validator.getFileItems()) ? null : validator.getFileItems().get(0).getInputStream();
                    this.usersService.add(user, imageStream);
                    req.getSession().setAttribute("registered", "");
                    resp.sendRedirect("login");
                    return;
                } else {
                    req.setAttribute("userExist", String.format("User with emаil = %s already exist", user.getEmail()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("globalError", "");
        }

        req.getRequestDispatcher("WEB-INF/pages/register.jsp").forward(req, resp);
    }

    private static RequestValidator<User> validate(HttpServletRequest request) throws FileUploadException {
        String name = null;
        String surname = null;
        String email = null;
        String password = null;
        String confirmPassword = null;
        List<FileItem> fileItems = new ArrayList<>();

        if(ServletFileUpload.isMultipartContent(request)){
            FileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            List<FileItem> items = upload.parseRequest(request);

            for (FileItem item : items) {
                if (item.isFormField()){
                    switch (item.getFieldName()){
                        case "name":
                            name = item.getString();
                            break;
                        case "surname":
                            surname = item.getString();
                            break;
                        case "email":
                            email = item.getString();
                            break;
                        case "password":
                            password = item.getString();
                            break;
                        case "confirmPassword":
                            confirmPassword = item.getString();
                            break;
                    }
                } else {
                    if(item.getSize() > 0){
                        fileItems.add(item);
                    }
                }
            }
        } else {
            name = request.getParameter("name");
            surname = request.getParameter("surname");
            email = request.getParameter("eamil");
            password = request.getParameter("password");
            confirmPassword = request.getParameter("confirmPassword");
        }

        boolean hasError = false;

        if(DataValidator.isNullOrBlank(name)){
            request.setAttribute("errorName", "Name is required!");
            hasError = true;
        }
        if(DataValidator.isNullOrBlank(surname)){
            request.setAttribute("errorSurname", "Surname is required!");
            hasError = true;
        }
        if(DataValidator.isNullOrBlank(email)){
            request.setAttribute("errorEmail", "Email is required!");
            hasError = true;
        } else if (!DataValidator.isValidEmail(email)){
            request.setAttribute("errorEmial", "Wrong email format!");
            hasError = true;
        }
        if(DataValidator.isNullOrBlank(password)){
            request.setAttribute("password", "Password is required!");
            hasError = true;
        } else if (DataValidator.isNullOrBlank(confirmPassword) || !password.equals(confirmPassword)){
            request.setAttribute("errorConfirmPassword", "Passwords doesn`t match!");
            hasError = true;
        }

        RequestValidator<User> validator = new RequestValidator<>();
        if(!hasError){
            User user = new User();

            user.setName(name);
            user.setSurname(surname);
            user.setEmail(email);
            user.setPassword(EncryptionUtil.encrypt(password));

            validator.setValue(user);
            validator.setFileItems(fileItems);

        } else {
            validator.setHasError(true);
        }

        return validator;
    }
}
