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

@WebServlet(urlPatterns = "/login.html")
public class LoginController extends HttpServlet
{
    private static final Logger logger = LogManager.getLogger(UserController.class.getName());
    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        System.out.println(req.getRequestURI() + " GETTED");    // Just to signify GETs, does what PostMan does
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {


        // Extract the request payload in JSON form from the BufferedReader on the request object
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user = null;

        // We unmarshall the JSON string into a Java instance of the User class
        try {
            user = mapper.readValue(JSON, User.class);

            // we have a new user object -> what do we do with it?
            // try and persist it to the database, however we should not go to our Repository directly.
            // We should instead pass this variable to the UserService so that it can handle sending to the DAO.
            List<User> users = userService.getAll();

            for (User u: users)
            {
                if (user.getUsername().equals(u.getUsername()))
                {
                    user = u;
                    logger.info("Found: " + user);
                    JSON = mapper.writeValueAsString(user);
                    logger.info(JSON);
                    resp.setContentType("application/json");
                    resp.getOutputStream().println(JSON);
                }
            }

            logger.info(user.toString());
        } catch (Exception e) {
            logger.warn(e);
        }


        // 200 - OK, 201 - Created is good if you're returning, 204 - No Content
        resp.setStatus(200);
    }
}
