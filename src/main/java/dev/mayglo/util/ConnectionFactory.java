package dev.mayglo.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;

/**
 * Connection Factory creates a single instance of the SQL connection needed for this application to
 * communicate with the database. If a Connection already exists, it will not create another.
 */
public class ConnectionFactory {
    static Logger logger = LogManager.getLogger(ConnectionFactory.class.getName());

    private static Connection instance;
    private static String url = "";
    private static String username = "";
    private static String password = "";

    /**
     * If there is no Connection or the Connection is closed, this method creates a Connection
     * with DriverManager's getConnection() method.
     * Otherwise, it returns the current connection.
     *
     * @return The Connection, named "instance"
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Map<String, String> env = System.getenv();
        for (String envName : env.keySet()) {
            if (envName.equals("DB_USER")) {
                username = env.get(envName);
                logger.debug("The username is " + username);
            }
            if (envName.equals("DB_PASSWORD")) {
                password = env.get(envName);
                logger.debug("The password is " + password);
            }
            if (envName.equals("DB_URL")) {
                url = env.get(envName);
                logger.debug("The url is " + url);
            }
        }

        if (instance == null || instance.isClosed()) {
            Class.forName("org.postgresql.Driver");
            instance = DriverManager.getConnection(url, username, password);
            logger.info("DB Connection started");
        }

        return instance;
    }

    /**
     * Initializes a ConnectionFactory object.
     */
    private ConnectionFactory() {
    }
}
