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

        try {
            JSON = mapper.writeValueAsString(users);

            resp.setContentType(JSON);
            resp.setStatus(200);
            resp.getOutputStream().println(JSON);
            // Webpage output:
            resp.getOutputStream().println(JSON);

        } catch (Exception e) {
            logger.warn(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // TODO: deactivate a user:
        /*
        String JSONUser = req.getReader().lines().collect(Collectors.joining());
        User userToDeactivate = null;

        try {
            userToDeactivate = mapper.readValue(JSONUser, User.class);
            userToDeactivate.setIs_Active(false);
            userService.update(userToDeactivate);

        } catch (Exception e) {
            logger.warn(e);
        }

         */

        // Extract the request in JSON form from the BufferedReader on the request object
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user = null;

        // Unmarshall the JSON string into a Java instance of the User class
        try {
            user = mapper.readValue(JSON, User.class);

            userService.create(user);
            logger.info(user.toString());
        } catch (Exception e) {
            logger.warn(e);
        }

        resp.setStatus(204);
    }

    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // delete a user:
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User userToDelete = null;

        try {
            userToDelete = mapper.readValue(JSON, User.class);
            userService.delete(userToDelete);

        } catch (Exception e) {
            logger.warn(e);
        }
    }

}