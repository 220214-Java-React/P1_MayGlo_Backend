import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        Logger logger = LogManager.getLogger(Log4JAlertsTest.class.getName());
        Connection connection = null;

        try {
            connection = ConnectionFactory.getConnection();
            String sql = "insert into testusers (username, password) values (?, ?)";

            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, "TEST USER");
            stmt.setString(2, "TEST PASSWORD");

            stmt.executeUpdate();

        } catch (SQLException e) {
            logger.error(e);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    logger.error(e);
                    e.printStackTrace();
                }
            }
        }
    }
}
