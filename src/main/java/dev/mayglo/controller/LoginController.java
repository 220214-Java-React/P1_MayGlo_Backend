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

@WebServlet(urlPatterns = "/login/*")
public class LoginController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(LoginController.class.getName());
    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req.getRequestURI() + " retrieved");    // Just to signify GETs, does what PostMan does
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Extract the request payload in JSON form from the BufferedReader on the request object
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user;

        // We unmarshall the JSON string into a Java instance of the User class
        try {
            // Map and return this as the JSON response if login information is incorrect.
            User dummyUser = new User(
                    -1,
                    "null",
                    "null",
                    "null",
                    "null",
                    "null",
                    true,
                    -1
            );

            user = mapper.readValue(JSON, User.class);  // Map request info (JSON) to a User object
            User DBUser = userService.getByUsername(user.getUsername());    // Find a matching username in database

            // If a user is found
            if (DBUser != null) {
                // If the password matches
                if (userService.checkUser(DBUser, user)) {
                    logger.info("Found: " + DBUser);
                    JSON = mapper.writeValueAsString(DBUser);
                    logger.debug(JSON);
                    resp.getOutputStream().println(JSON);
                } else {
                    logger.debug("User found but password was incorrect.");
                    JSON = mapper.writeValueAsString(dummyUser);
                    resp.getOutputStream().println(JSON);
                }
                logger.info(user.toString());
            } else {
                logger.debug("User not found");
                JSON = mapper.writeValueAsString(dummyUser);
                resp.getOutputStream().println(JSON);
            }
        } catch (Exception e) {
            logger.warn(e);
        }
    }
}