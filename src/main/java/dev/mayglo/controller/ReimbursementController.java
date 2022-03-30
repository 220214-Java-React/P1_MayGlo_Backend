package dev.mayglo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayglo.model.Reimbursement;
import dev.mayglo.service.ReimbService;
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

@WebServlet(urlPatterns = "/reimbursements/*")
public class ReimbursementController extends HttpServlet
{
    private static final int CREATED = 201;
    private static final Logger logger = LogManager.getLogger(UserController.class.getName());
    private final ReimbService reimbService = new ReimbService();
    private final ObjectMapper mapper = new ObjectMapper();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String clientID = req.getParameter("user_ID");  // User ID
        String role_ID = req.getParameter("role_ID");   // Role ID
        logger.info(clientID);
        List<Reimbursement> reimbursements;

        if (role_ID.equals("0")) // If it is an employee
        {
            reimbursements = reimbService.getByAuthorID(Integer.parseInt(clientID));    // Get reimbursements via ID

            String JSON = mapper.writeValueAsString(reimbursements);    // Marshall into JSON
            resp.setContentType("application/json");                    // Set content type
            resp.setStatus(200);                                        // Set Status
            resp.getOutputStream().println(JSON);                       // Send reimbursements to frontend

            logger.info(JSON);
        }
        else        // It is a manager
        {
            reimbursements = reimbService.getAllForManagers();          // Get all reimbursements

            String JSON = mapper.writeValueAsString(reimbursements);    // Marshall into JSON
            resp.setContentType("application/json");                    // Set content type
            resp.setStatus(200);                                        // Set status
            resp.getOutputStream().println(JSON);                       // Send to frontend

            logger.info(JSON);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String JSON = req.getReader().lines().collect(Collectors.joining());    // Extract request into JSON form (String object)
        String clientID = req.getParameter("userID");
        Reimbursement reimb;    // Declare Reimbursement object

        try
        {
            reimb = mapper.readValue(JSON, Reimbursement.class);    // Map request info to Reimbursement object
            reimb.setAuthor_ID(Integer.parseInt(clientID));         // Set Author
            reimbService.create(reimb);         // Create a reimbursement and persist it to database
            resp.setStatus(CREATED);            // Tell client reimbursement was created
        }
        catch (Exception e)
        {
            logger.warn(e);
        }
    }
}
