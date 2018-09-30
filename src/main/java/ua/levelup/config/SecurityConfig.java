package ua.levelup.config;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public enum SecurityConfig {
    ;

    private static final Logger logger = LogManager.getLogger();

    private static Set<String> protectedURLs;
    private static Set<String> protectedFromAdminURLs;
    private static Set<String> protectedFromActiveURLs;
    private static Set<String> protectedFromBlockedURLs;
    private static final String protectedUrlsFileName = PropertiesManager.getApplicationProperties()
            .getProperty("protected.urls");
    private static final String protectedFromAdminUrlsFileName = PropertiesManager.getApplicationProperties()
            .getProperty("protected.from.admin.urls");
    private static final String protectedFromActiveUrlsFileName = PropertiesManager.getApplicationProperties()
            .getProperty("protected.from.active.urls");
    private static final String protectedFromBlockedUrlsFileName = PropertiesManager.getApplicationProperties()
            .getProperty("protected.from.blocked.urls");

    static {
        protectedURLs = new HashSet<>();
        getProtectedUrlsStream(protectedUrlsFileName)
                .forEach((url) -> protectedURLs.add(url));
        protectedFromAdminURLs = new HashSet<>();
        getProtectedUrlsStream(protectedFromAdminUrlsFileName)
                .forEach((url) -> protectedFromAdminURLs.add(url));
        protectedFromActiveURLs = new HashSet<>();
        getProtectedUrlsStream(protectedFromActiveUrlsFileName)
                .forEach((url) -> protectedFromActiveURLs.add(url));
        protectedFromBlockedURLs = new HashSet<>();
        getProtectedUrlsStream(protectedFromBlockedUrlsFileName)
                .forEach((url) -> protectedFromBlockedURLs.add(url));
    }

    public static Set<String> getProtectedURLs(ProtectedUrlType protectedUrl) {
        switch(protectedUrl){
            case ALL: return protectedURLs;
            case ADMIN: return protectedFromAdminURLs;
            case ACTIVE: return protectedFromActiveURLs;
            case BLOCKED: return protectedFromBlockedURLs;
            default: throw new IllegalStateException("Unsupported ProtectedUrl");
        }
    }

    private static Stream<String> getProtectedUrlsStream(String fileName){
        try {
            URI uri = Thread.currentThread().getContextClassLoader()
                    .getResource(fileName).toURI();
            return Files.lines(Paths.get(uri));
        } catch (URISyntaxException e) {
            logger.error("Exception: " + e.getClass() +
                    "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_GET_FILE_URI"), e);
        } catch (IOException e) {
            logger.error("Exception: " + e.getClass() +
                    "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_READ_FILE") + fileName, e);
        }
    }

    public enum ProtectedUrlType {
        ALL,ADMIN,ACTIVE,BLOCKED;
    }
}
