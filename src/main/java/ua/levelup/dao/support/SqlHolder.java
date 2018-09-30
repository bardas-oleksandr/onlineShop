package ua.levelup.dao.support;

import ua.levelup.config.PropertiesManager;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public enum SqlHolder {
    ;

    private final static String sqlQueryFileName = PropertiesManager.getApplicationProperties()
            .getProperty("sql.query.properties");
    private static Properties properties;

    static {
        properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(sqlQueryFileName)) {
            properties.load(input);
        } catch (IOException e) {
            properties = null;
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_READ_FILE") + sqlQueryFileName, e);
        }
    }

    public static Properties getSqlProperties() {
        return properties;
    }
}
