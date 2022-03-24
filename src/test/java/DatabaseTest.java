import dev.mayglo.model.Reimbursement;
import dev.mayglo.model.User;
import dev.mayglo.service.UserService;
import dev.mayglo.util.ConnectionFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class DatabaseTest
{
    UserService userService;

    @BeforeEach
    public void createNewUserService()
    {
        userService = new UserService();
    }

    @Test
    @DisplayName("Test connecting to database")
    public void testConnection() {
        Assertions.assertDoesNotThrow(ConnectionFactory :: getConnection);
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

        Assertions.assertDoesNotThrow(() -> userService.create(user));
    }

    @Test
    public void createReimbursement()
    {
        Reimbursement reimbursement = new Reimbursement();
    }
}
