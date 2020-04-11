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
import java.util.*;

@WebServlet("/usersList")
public class UserListServlet extends BaseUserServlet {

    private Map<Integer, Date> usersActivityMap;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (usersActivityMap == null) {
                usersActivityMap = (Map<Integer, Date>) req.getServletContext().getAttribute("users_activity");
            }
            resp.setContentType("application/json");

            List<User> users = super.usersService.getAllUsers();
            List<UserView> userViewList = new ArrayList<>(users.size());

            for (User user : users) {
                UserView userView = new UserView();
                userView.id = user.getId();
                userView.fullName = user.getName() + user.getSurname();
                userView.imageUrl = user.getImageUrl();
                Date activityDate = usersActivityMap.get(user.getId());
                userView.active = (activityDate != null &&
                        System.currentTimeMillis() - activityDate.getTime() < 15 * 60 * 1000);

                userViewList.add(userView);
            }

            userViewList.sort(Comparator.comparing(UserView::isActive).reversed());
            try (Writer writer = resp.getWriter()) {
                writer.write(JsonUtil.serialize(userViewList));
            }
        } catch (DatabaseException e) {
            e.printStackTrace();
        }
    }

    private class UserView {
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


