package ua.levelup.exception.support;

import ua.levelup.config.PropertiesManager;
import ua.levelup.exception.ApplicationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum MessageHolder {
    ;

    private final static String errorMessagesFileName = PropertiesManager.getApplicationProperties()
            .getProperty("exception.messages.properties");
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(errorMessagesFileName)) {
            properties.load(input);
        } catch (IOException e) {
            properties = null;
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_READ_FILE") + errorMessagesFileName, e);
        }
    }

    public static Properties getMessageProperties() {
        return properties;
    }
}
