package dev.mayglo.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UserServlet extends HttpServlet {

    /**
     * Requesting a resource
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response status on get
        resp.setStatus(200);

        // A custom-made string. Use "marshalling" instead like a normal person
        String json = "{\"id\": 1, \"name\": \"Donald Duck\"}";

        // Tell the browser what type of content is being returned
        resp.setContentType(json);

        // What will actually be outputted to the page
        resp.getOutputStream().println(json);
    }
}