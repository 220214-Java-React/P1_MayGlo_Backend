package dev.mayglo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayglo.model.User;
import dev.mayglo.model.UserRole;
import dev.mayglo.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Provides access to RESTful methods to manipulate Users.
 */
@WebServlet(urlPatterns = "/users/*")
public class UserController extends HttpServlet {

    private static final Logger logger = LogManager.getLogger(UserController.class.getName());
    private final UserService userService = new UserService();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns all users by default. Allows the following options via parameters:
     * <ul>
     * <li>Search by ID</li>
     * <li>Search by is_Active</li>
     * <li>Search by role_ID</li>
     * </ul>
     *
     * @param req  Request that was received
     * @param resp Response to return
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String JSON = "";
        String byID = req.getParameter("user_id");
        String byis_Active = req.getParameter("is_Active");
        String byrole_ID = req.getParameter("role_ID");
        String byUsername = req.getParameter("username");

        // Map and return this as the JSON response if no user is found.
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

        if (byUsername != null)
        {
            User userByUsername = userService.getByUsername(byUsername);    // Get user object
            if (userByUsername != null) {
                logger.debug(userByUsername.toString());                        // Log what was found
                JSON = mapper.writeValueAsString(userByUsername);               // Marshall into JSON
                resp.setContentType("application/json");                        // Set Content Type
                resp.setStatus(200);                                            // Set Status
                resp.getOutputStream().println(JSON);                           // Send User back to client
                System.out.print(JSON);
            } else {
                logger.debug("Could not find user.");
                userByUsername = dummyUser;
                JSON = mapper.writeValueAsString(userByUsername);               // Marshall into JSON
                resp.setContentType("application/json");                        // Set Content Type
                resp.setStatus(200);                                            // Set Status
                resp.getOutputStream().println(JSON);                           // Send User back to client
                System.out.print(JSON);
            }
        }


        // Search by ID
        if (byID != null) {
            User userByID = userService.getByID(Integer.parseInt(byID));
            logger.debug(userByID.toString());
            JSON = mapper.writeValueAsString(userByID);
            resp.setContentType(JSON);
            resp.setStatus(200);
            resp.getOutputStream().println(JSON);
        }

        // Search by is_Active
        if (byis_Active != null) {
            List<User> usersByActivity = new ArrayList<>();
            for (User user : userService.getAll()) {
                if (user.getIs_Active().equals(Boolean.parseBoolean(byis_Active))) {
                    usersByActivity.add(user);
                    logger.debug("Added " + user.getUsername());
                }
            }
            JSON = mapper.writeValueAsString(usersByActivity);
            resp.setContentType(JSON);
            resp.setStatus(200);
            resp.getOutputStream().println(JSON);
        }

        // Search by role_ID
        if (byrole_ID != null) {

            // Show enum text value of role
            int roleInt = Integer.parseInt(byrole_ID);
            UserRole enumVal = UserRole.values()[roleInt];
            logger.debug(enumVal);

            List<User> usersByRole = new ArrayList<>();
            for (User user : userService.getAll()) {
                if (user.getRole_ID().equals(Integer.parseInt(byrole_ID))) {
                    usersByRole.add(user);
                    logger.debug("Added " + user.getUsername());
                }
            }
            JSON = mapper.writeValueAsString(usersByRole);
            resp.setContentType(JSON);
            resp.setStatus(200);
            resp.getOutputStream().println(JSON);

        } else if (byUsername == null && byID == null && byis_Active == null && byrole_ID == null) {

            try {
                List<User> users = userService.getAll();

                JSON = mapper.writeValueAsString(users);
                resp.setContentType(JSON);
                resp.setStatus(200);
                // Webpage output:
                resp.getOutputStream().println(JSON);

            } catch (Exception e) {
                logger.warn(e);
            }
        }
    }

    /**
     * Creates a user.
     *
     * @param req  Request that was received
     * @param resp Response to return
     * @throws ServletException
     * @throws IOException
     */
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        final int CREATED = 201;
        final int ERROR = 406;
        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user = null;
        logger.info(JSON);

        try {
            // Unmarshall the JSON string into a Java instance of the User class
            user = mapper.readValue(JSON, User.class);
            userService.create(user);
            logger.debug(user.toString());
            resp.setStatus(CREATED);
        } catch (Exception e) {
            logger.warn(e);
            resp.setStatus(ERROR);
        }
    }

    /**
     * Updates a user by their ID number. The PUT request must include an ID parameter.
     *
     * @param req  Request that was received
     * @param resp Response to return
     * @throws ServletException
     * @throws IOException
     */
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String JSON = req.getReader().lines().collect(Collectors.joining());     // What's read from request
        String updateByID = req.getParameter("update");                     // Parameter during PUT request

        // Update an existing user
        if (updateByID != null) {
            // Parse the integer value of the ID from the provided string
            int userID = Integer.parseInt(updateByID);

            // Create a temporary User with the new values
            User updatedUser = mapper.readValue(JSON, User.class);
            updatedUser.setUser_ID(userID);
            String encryptedPassword = userService.encryptPassword(updatedUser.getPassword());
            updatedUser.setPassword(encryptedPassword);
            logger.debug(updatedUser.toString());

            // Get the User to be updated
            User accountToUpdate = userService.getByID(userID);
            logger.debug(accountToUpdate.toString());

            // Set all values
            accountToUpdate.setAll(updatedUser);

            // Update the account
            userService.update(accountToUpdate);
            resp.setStatus(200);    // Set Response status
        }
    }

    /**
     * Deletes a user by their user_ID.
     *
     * @param req  Request that was received
     * @param resp Response to return
     * @throws ServletException
     * @throws IOException
     */
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String byID = req.getParameter("id");

        String JSON = req.getReader().lines().collect(Collectors.joining());
        User userToDelete = null;

        // Update an existing user
        if (byID != null) {
            // Parse the integer value of the ID from the provided string
            int userID = Integer.parseInt(byID);

            // Delete the account
            userService.delete(userService.getByID(userID));
            resp.setStatus(200);
        }
    }
}

