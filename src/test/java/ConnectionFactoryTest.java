import dev.mayglo.model.User;
import dev.mayglo.service.UserService;
import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.async.ArrayBlockingQueueFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactoryTest {

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

        UserService userService = new UserService();
        Assertions.assertDoesNotThrow(() -> userService.create(user));
    }
}
