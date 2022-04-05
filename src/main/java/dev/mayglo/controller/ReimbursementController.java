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

/**
 * Provides access to RESTful methods to manipulate and retrieve Reimbursements.
 */
@WebServlet(urlPatterns = "/reimbursements/*")
public class ReimbursementController extends HttpServlet
{
    private static final int CREATED = 201;
    private static final Logger logger = LogManager.getLogger(UserController.class.getName());
    private final ReimbService reimbService = new ReimbService();
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Returns Reimbursement items depending on the requester's role_ID.
     * Employee records are retrieved with their "clientID" (user_ID).
     * Managers return all records with ReimbService's getAllForManagers method.
     *
     * @param req  Request that was received
     * @param resp Response to return
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String clientID = req.getParameter("user_ID");  // User ID
        String role_ID = req.getParameter("role_ID");   // Role ID
        String reimbToGet = req.getParameter("reimb_ID");   // Reimbursement ID
        logger.info(clientID);

        List<Reimbursement> reimbursements;

        if (role_ID.equals("0")) // If it is an employee
        {
            if (reimbToGet == null)
            {
                reimbursements = reimbService.getByAuthorID(Integer.parseInt(clientID));    // Get reimbursements via ID

                String JSON = mapper.writeValueAsString(reimbursements);    // Marshall into JSON
                resp.setContentType("application/json");                    // Set content type
                resp.setStatus(200);                                        // Set Status
                resp.getOutputStream().println(JSON);                       // Send reimbursements to frontend

                logger.info(JSON);
            }
            else
            {
                Reimbursement reimbursement = reimbService.getReimbursementByID(Integer.parseInt(reimbToGet));  // Get reimbursement needed
                String JSON = mapper.writeValueAsString(reimbursement);     // Marshall it
                resp.setContentType("application/json");                    // Set content type
                resp.setStatus(200);                                        // Set status
                resp.getOutputStream().println(JSON);                       // Send reimbursement to frontend
            }
        }
        else        // It is a manager
        {
            String pending = req.getParameter("pending");       // Pending parameter
            int getPending = Integer.parseInt(pending);             // Get only the pending reimbursements?

            reimbursements = reimbService.getAllForManagers(getPending);          // Get all reimbursements

            String JSON = mapper.writeValueAsString(reimbursements);    // Marshall into JSON
            resp.setContentType("application/json");                    // Set content type
            resp.setStatus(200);                                        // Set status
            resp.getOutputStream().println(JSON);                       // Send to frontend

            logger.info(JSON);
        }
    }

    /**
     * Creates a Reimbursement. Takes the "clientID" (user_ID) as a parameter to denote the request creator.
     *
     * @param req  Request that was received
     * @param resp Response to return
     * @throws ServletException
     * @throws IOException
     */
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

    /**
     * Updates a reimbursement. Employees may update a pending reimbursement.
     * Managers may update a reimbursement by approving or denying it.
     * Only managers may approve/deny reimbursements.
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        String JSON = req.getReader().lines().collect(Collectors.joining());    // Extract request into JSON form (String object)
        String clientID = req.getParameter("user_ID");  // User ID param
        String role_ID = req.getParameter("role_ID");   // Role ID param
        logger.info(JSON);

        Reimbursement reimb;

        try
        {
            if (role_ID.equals("0"))    // If it's an employee
            {
                reimb  = mapper.readValue(JSON, Reimbursement.class);   // Unmarshall
                reimbService.updateEmployeeReimb(reimb);    // Update it with values from front end
                resp.setStatus(200);                        // Set status
            }
            else        // It's a manager
            {
                reimb  = mapper.readValue(JSON, Reimbursement.class);               // Unmarshall
                int tempStatus = reimb.getStatus_ID();                              // Store status
                reimb = reimbService.getReimbursementByID(reimb.getReimb_ID());     // Find matching reimbursement in database
                reimb.setStatus_ID(tempStatus);                                     // Set status of reimbursement
                reimb.setResolver_ID(Integer.parseInt(clientID));                   // Set resolver ID of reimbursement
                reimbService.updateResolved(reimb);                                 // Update reimbursement
                resp.setStatus(200);                                                // Set status
            }
        }
        catch (Exception e)
        {
            logger.warn(e);
        }
    }
}
