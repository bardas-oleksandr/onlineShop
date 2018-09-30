package ua.levelup.config;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum PropertiesManager {
    ;

    private static final Logger logger = LogManager.getLogger();

    private final static String PROPERTY_FILE_NAME = "application.properties";

    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(PROPERTY_FILE_NAME)) {
            properties.load(input);
        } catch (IOException e) {
            properties = null;
            logger.error("Exception: " + e.getClass() +
                    "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_READ_FILE" + PROPERTY_FILE_NAME), e);
        }
    }

    public static Properties getApplicationProperties() {
        return properties;
    }
}
