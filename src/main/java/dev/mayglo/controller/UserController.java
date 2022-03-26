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

import java.util.ArrayList;
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

        String JSON = "";
        String byID = req.getParameter("user_id");
        String byis_Active = req.getParameter("is_Active");
        String byrole_ID = req.getParameter("role_ID");

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

        } else if (byID == null && byis_Active == null && byrole_ID == null) {

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

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user = null;

        try {
            // Unmarshall the JSON string into a Java instance of the User class
            user = mapper.readValue(JSON, User.class);
            userService.create(user);
            logger.debug(user.toString());
            resp.setStatus(204);
        } catch (Exception e) {
            logger.warn(e);
        }
    }

    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String JSON = req.getReader().lines().collect(Collectors.joining());
        User user = null;

        String updateByID = req.getParameter("update");

        // Update an existing user
        if (updateByID != null) {
            // Parse the ID from the provided string
            int userID = Integer.parseInt(updateByID);

            // fill "user" with our new values
            User updatedUser = mapper.readValue(JSON, User.class);
            logger.debug(updatedUser.toString());

            // Get the existing User we want to update
            User accountToUpdate = userService.getByID(userID);
            logger.debug(accountToUpdate.toString());

            // Set all values
            accountToUpdate.setAll(updatedUser);

            // Update the account
            userService.update(accountToUpdate);
        }
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

}

