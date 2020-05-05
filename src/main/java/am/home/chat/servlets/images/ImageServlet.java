package am.home.chat.servlets.images;

import am.home.chat.utils.db.Settings;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/images/*")
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String url = req.getRequestURI();
        String imageName = url.substring("/images/".length());
        String path = Settings.getInstance().getString("image.dir");
        String imagePath = path + imageName;



        resp.setContentType("image/*");
        try(FileInputStream fi = new FileInputStream(imagePath);
            OutputStream out = resp.getOutputStream()) {
            byte[] buff = new byte[2048];
            int readCount;

            while ((readCount = fi.read(buff)) > -1){
                out.write(buff, 0, readCount);
            }
        }
   }
}
