package dev.mayglo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayglo.model.Reimbursement;
import dev.mayglo.model.User;
import dev.mayglo.service.ReimbService;
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

        int cuRole = LoginController.currentUser.getRole_ID();
        switch (cuRole) {
            case 0:
                // Employee. Get only those associated with this user
                //reimbService.getByUsername(LoginController.currentUser.getUsername());
                break;
            case 1:
                // Manager. Get all.
                reimbService.getAllReimbursements();

            case 2:
                // Admin. Same as above.
                break;
        }

        String clientID = req.getParameter("user_ID");
        logger.info(clientID);

        List<Reimbursement> reimbursements;

        reimbursements = reimbService.getAllReimbursements(Integer.parseInt(clientID));

        String JSON = mapper.writeValueAsString(reimbursements);
        resp.setContentType("application/json");
        resp.setStatus(200);
        resp.getOutputStream().println(JSON);

        logger.info(JSON);
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
