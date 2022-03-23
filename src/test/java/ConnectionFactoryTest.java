import dev.mayglo.util.ConnectionFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ConnectionFactoryTest {

    @Test
    @DisplayName("Test the connection to the PostgreSQL database")
    public void create() {
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

    class User {
        private String username;
        private String password;

        /**
         * Creates a new User. Does not take arguments.
         */
        public User() {
        }

        /**
         * Creates a User with the following parameters:
         *
         * @param username The username associated with this User account
         * @param password The password associated with this User account
         */
        public User(String username, String password) {
            this.username = username;
            this.password = password;
        }

    }
}
