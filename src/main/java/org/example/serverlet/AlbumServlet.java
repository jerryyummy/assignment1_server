package org.example.serverlet;

import org.example.bean.Album;
import com.google.gson.Gson;
import org.example.bean.Profile;
import org.example.bean.Status;
import java.io.IOException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Objects;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

public class AlbumServlet extends HttpServlet {
    private static final long serialVersionUID = 205242440643911308L;
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();

        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // Parse the request to get a list of file items
            List<FileItem> items = upload.parseRequest(request);
            byte[] imageData = null;

            for (FileItem item : items) {
                if (!item.isFormField()) {
                    // Process file field (image)
                    // Get the input stream of the uploaded file
                    InputStream fileContent = item.getInputStream();

                    // Convert the input stream to binary data (byte array)
                    imageData = IOUtils.toByteArray(fileContent);
                }
            }

            Profile profile = new Profile(request.getParameter("artist"),
                    request.getParameter("year"), request.getParameter("title"));
            Album album = new Album(request.getParameter("albumID"), imageData.toString(),profile);
            Status status = new Status();

            if (!Objects.equals(album.getAlbumID(), "")) {
                status.setSuccess(true);
                status.setDescription("success");
            } else {
                status.setSuccess(false);
                status.setDescription("fail");
            }
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().print("/n"+imageData.length*8);
            response.getOutputStream().flush();
        } catch (Exception ex) {
            ex.printStackTrace();
            Status status = new Status();
            status.setSuccess(false);
            status.setDescription(ex.getMessage());
            response.getOutputStream().print(gson.toJson(status));
            response.getOutputStream().flush();
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response) throws IOException {

        Album album = new Album("1", "[B@67b1bc18", new Profile("Karan","1977","IT"));
        String albumJsonString = new Gson().toJson(album);

        PrintWriter out = response.getWriter();
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        out.print(albumJsonString);
        out.flush();
    }
}