import com.fasterxml.jackson.databind.ObjectMapper;
import dev.mayglo.model.ReimbStatus;
import dev.mayglo.model.Reimbursement;
import dev.mayglo.model.User;
import dev.mayglo.service.ReimbService;
import dev.mayglo.service.UserService;
import dev.mayglo.util.ConnectionFactory;
import static org.junit.jupiter.api.Assertions.*;
//import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

public class DatabaseTest
{
    UserService userService;
    ReimbService reimbService;

    @BeforeEach
    public void createNewUserService()
    {
        userService = new UserService();
        reimbService = new ReimbService();
    }

    @Test
    @DisplayName("Test connecting to database")
    public void testConnection() {
        assertDoesNotThrow(ConnectionFactory :: getConnection);
    }

    @Test
    @DisplayName("Test creating a user in the PostgreSQL database")
    public void createUser() {
        User user = new User(
                "CTESTER",
                "abc",
                "CTester@gmail.com",
                "Chester", "Tester", true, 0
        );

        assertDoesNotThrow(() -> userService.create(user));
    }

    @Test
    @DisplayName("Get a User by their ID")
    public void getAUserByID() {
        assertDoesNotThrow(() -> userService.getByID(1));
    }

    @Test
    @DisplayName("Get all Users")
    public void getAllUsers() {
        assertDoesNotThrow(() -> userService.getAll());
    }

    @Test
    @DisplayName("Create and delete a test user")
    public void deleteAUser() {
        User user = userService.getByID(7);
        assertDoesNotThrow(() -> userService.delete(user));
    }

    @Test
    @DisplayName("Test creating a reimbursement in the PostgreSQL database")
    public void createReimbursement()
    {
        User u = userService.getByID(1);

        assertNotNull(u);

        Reimbursement reimbursement = new Reimbursement(50.50, u.getID());
        reimbursement.setType_ID(0);

        assertNotNull(reimbursement);

        reimbursement.setStatus_ID(ReimbStatus.PENDING.ordinal());

        assertDoesNotThrow(()-> reimbService.create(reimbursement));
    }

    @Test
    @DisplayName("Test retrieving all reimbursements in database")
    public void getAllReimbursementsFromDatabase()
    {
        assertDoesNotThrow(()->reimbService.getAll());

        List<Reimbursement> rbs = reimbService.getAll();

        assertNotNull(rbs);
    }

    @Test
    @DisplayName("Test retrieving all reimbursements for a specific user")
    public void getAllReimbursementsByAuthorID()
    {
        ObjectMapper mapper = new ObjectMapper();
        assertDoesNotThrow(() -> reimbService.getByAuthorID(8));

        assertDoesNotThrow(() -> mapper.writeValueAsString(reimbService.getByAuthorID(8)));
    }
}
