//package ua.levelup.exception.support;
//
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ua.levelup.exception.ApplicationException;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//@Component("messageContainer")
//@Setter
//public class MessageContainer {
//
//    @Autowired
//    private Properties applicationProperties;
//
//    private Properties properties;
//
//    private final String errorMessagesFileName = applicationProperties
//            .getProperty("exception.messages.properties");
//
//    @PostConstruct
//    public void init(){
//        properties = new Properties();
//        try (InputStream input = Thread.currentThread().getContextClassLoader()
//                .getResourceAsStream(errorMessagesFileName)) {
//            properties.load(input);
//        } catch (IOException e) {
//            properties = null;
//            throw new ApplicationException("Failed to read file: " + errorMessagesFileName, e);
//        }
//    }
//
//    public Properties getMessageProperties() {
//        return properties;
//    }
//}
