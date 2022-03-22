import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class Log4JAlertsTest {

    @Test
    @DisplayName("Log 4 J test")
    void writeAlerts() throws InterruptedException {
        Logger logger = LogManager.getLogger(Log4JAlertsTest.class.getName());

        logger.trace("Trace");
        logger.debug("Debug");
        logger.info("Info");
        logger.warn("Warn");
        logger.error("Error");
        logger.fatal("Fatal");
    }

}
