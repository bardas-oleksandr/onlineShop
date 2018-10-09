package ua.levelup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ua.levelup.exception.ApplicationException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Configuration
public class ExceptionMessagesPropertiesConfig {

    @Autowired
    private Properties applicationProperties;

    @Bean("messagesProperties")
    public Properties exceptionMessagesProperties(){
        String exceptionMessagesFileName = applicationProperties
                .getProperty("exception.messages.properties");
        Properties properties = new Properties();
        try (InputStream input = Thread.currentThread().getContextClassLoader()
                .getResourceAsStream(exceptionMessagesFileName)) {
            properties.load(input);
        } catch (IOException e) {
            throw new ApplicationException("Failed to read file: " + exceptionMessagesFileName, e);
        }
        return properties;
    }
}
