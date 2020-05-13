package am.home.chat.servlets.users;

import am.home.chat.models.User;
import am.home.chat.services.csv.CsvCreator;
import am.home.chat.services.csv.impl.CsvCreatorImpl;
import am.home.chat.services.excel.ExcelCreator;
import am.home.chat.services.excel.impl.ExcelCreatorImpl;
import org.apache.poi.ss.usermodel.Workbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.util.List;

@WebServlet("/users/download")
public class UsersDownloadServlet extends BaseUserServlet {

    private final ExcelCreator excelCreator;
    private final CsvCreator csvCreator;

    public UsersDownloadServlet() {
        this.excelCreator = new ExcelCreatorImpl();
        this.csvCreator = new CsvCreatorImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            String type = req.getParameter("type");
            User currentUser = (User) req.getSession().getAttribute("user");
            List<User> users = super.usersService.getAllContacts(currentUser.getId());

            if ("xlsx".equalsIgnoreCase(type)) {
                resp.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
                resp.setHeader("Content-Disposition", "attachment; filename=users.xlsx");

                try (ByteArrayOutputStream out = new ByteArrayOutputStream();
                     OutputStream clientWrite = resp.getOutputStream();
                     Workbook workbook = this.excelCreator.createWorkbook(users)) {
                    workbook.write(out);
                    byte[] data = out.toByteArray();
                    for (int i = 0; i < data.length; i++) {
                        clientWrite.write(data[i]);
                        clientWrite.flush();
                        Thread.sleep(10);
                    }

                }
            } else {
                resp.setContentType("text/csv");
                resp.setHeader("Content-Disposition", "attachment; filename=users.csv");
                char[] data = this.csvCreator.createCsv(users);
                resp.setHeader("Content-length", data.length + "");
                try (Writer writer = resp.getWriter()) {
                    writer.write(data);
                    writer.flush();
                    Thread.sleep(10);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
