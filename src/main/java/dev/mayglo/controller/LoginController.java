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
import java.util.stream.Collectors;

@WebServlet(urlPatterns = "/login.html")
public class LoginController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginController.class.getName());
    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();
    public static User currentUser = new User();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String logOut = req.getParameter("logout");

        // Log out
        if (logOut != null)
        {
            logger.debug(currentUser.getUsername() + " logged out");
            currentUser = new User();
            resp.setContentType("text/plain");
            resp.getOutputStream().println("Logged out.");
        } else {
            System.out.println(req.getRequestURI() + " GET'd");    // Just to signify GETs, does what PostMan does
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Extract the request payload in JSON form from the BufferedReader on the request object
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user;

        // We unmarshall the JSON string into a Java instance of the User class
        try {
            user = mapper.readValue(JSON, User.class);  // Map request info (JSON) to a User object

            User DBUser = userService.getByUsername(user.getUsername());    // Find a matching username in database

            if (DBUser != null)      // If a user is found
            {
                if (userService.checkUser(DBUser, user))   // If the password matches
                {
                    logger.info("Found: " + DBUser);
                    currentUser = DBUser;
                    JSON = mapper.writeValueAsString(DBUser);
                    resp.setStatus(201);
                    resp.getOutputStream().println(JSON);
                }
                else    // Otherwise
                {
                    resp.setStatus(205);    // Another code that isn't listed above
                }

                logger.info(user.toString());
            }
        } catch (Exception e) {
            logger.warn(e);
        }
    }
}