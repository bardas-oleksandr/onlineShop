package ua.levelup.logger;

import org.apache.logging.log4j.Logger;

public interface ShopLogger {
    Logger getLogger();

    default void logError(Exception e) {
        getLogger().error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
    }

    default void logWarn(String warning) {
        getLogger().error("Warning: " + warning);
    }
}
