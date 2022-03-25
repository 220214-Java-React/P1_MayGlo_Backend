package dev.mayglo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayglo.model.User;
import dev.mayglo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/users")
public class UserController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());
    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();

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
        List<User> users = userService.getAll();
        String JSON = "";

        // Tell the browser what type of content is being returned
        resp.setContentType(JSON);

        try {
            JSON = mapper.writeValueAsString(users);

            // Tell the browser what type of content is being returned
            resp.setContentType(JSON);
            resp.setStatus(200);
            resp.getOutputStream().println(JSON);
            // What will actually be outputted to the page
            resp.getOutputStream().println(JSON);

        } catch (Exception e) {
            logger.warn(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // Extract the request payload in JSON form from the BufferedReader on the request object
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user = null;

        // We unmarshall the JSON string into a Java instance of the User class
        try {
            user = mapper.readValue(JSON, User.class);

            // we have a new user object -> what do we do with it?
            // try and persist it to the database, however we should not go to our Repository directly.
            // We should instead pass this variable to the UserService so that it can handle sending to the DAO.
            userService.create(user);
            logger.info(user.toString());
        } catch (Exception e) {
            logger.warn(e);
        }


        // 200 - OK, 201 - Created is good if you're returning, 204 - No Content
        resp.setStatus(204);
    }
}