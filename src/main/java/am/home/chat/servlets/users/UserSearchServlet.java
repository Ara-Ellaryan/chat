package am.home.chat.servlets.users;

import am.home.chat.exceptions.DatabaseException;
import am.home.chat.models.User;
import am.home.chat.utils.JsonUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@WebServlet("/userSearch")
public class UserSearchServlet extends BaseUserServlet {

    private Map<Integer, Date> usersActivityMap;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            String email = req.getParameter("email");
            if (usersActivityMap == null) {
                usersActivityMap = (Map<Integer, Date>) req.getServletContext().getAttribute("users_activity");
            }

            Optional<User> userOptional = this.usersService.get(email);
            String userInfo;

            if (!userOptional.isPresent()){
                userInfo =  "User whit searching email doesn`t exist!";
            } else {
                User user = userOptional.get();
                UserView userView = new UserView();
                userView.id = user.getId();
                userView.fullName = user.getName() + " " + user.getSurname();
                userView.imageUrl = user.getImageUrl();

                Date activeUserDate = usersActivityMap.get(user.getId());
                userView.active = (activeUserDate != null && System.currentTimeMillis() - activeUserDate.getTime() > 15 * 60 * 1000);

                userInfo = JsonUtil.serialize(userView);

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
    private class UserView{
        @JsonProperty("sender_id")
        int id;
        @JsonProperty("image_url")
        String imageUrl;
        @JsonProperty("full_name")
        String fullName;
        boolean active;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getFullName() {
            return fullName;
        }

        public void setFullName(String fullName) {
            this.fullName = fullName;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }
}
