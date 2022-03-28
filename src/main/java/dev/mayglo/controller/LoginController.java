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

            User DBUser = userService.getByUsername(user.getUsername());

            if (DBUser != null && userService.checkUser(DBUser, user))
            {
                logger.info("Found: " + DBUser);

                // 200 - OK, 201 - Created is good if you're returning, 204 - No Content
                resp.setStatus(204);
            }
            else
            {
                resp.setStatus(205);
            }

//            List<User> users = userService.getAll();
//
//            for (User u: users)
//            {
//                if (user.getUsername().equals(u.getUsername()))
//                {
//                    user = u;
//                    JSON = mapper.writeValueAsString(user);
//                    logger.info(JSON);
//                    resp.setContentType("application/json");
//                    resp.getOutputStream().println(JSON);
//                }
//            }

            logger.info(user.toString());
        } catch (Exception e) {
            logger.warn(e);
        }
    }
}
