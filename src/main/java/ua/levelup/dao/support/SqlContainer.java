//package ua.levelup.dao.support;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//
//import javax.annotation.PostConstruct;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.Properties;
//
//@Component("sqlContainer")
//public class SqlContainer {
//
//    @Autowired
//    private Properties applicationProperties;
//
//    @Autowired
//    private MessageContainer messageContainer;
//
//    private Properties properties;
//
//    private final String sqlQueryFileName = applicationProperties.getProperty("sql.query.properties");
//
//    @PostConstruct
//    public void init(){
//        properties = new Properties();
//        try (InputStream input = Thread.currentThread().getContextClassLoader()
//                .getResourceAsStream(sqlQueryFileName)) {
//            properties.load(input);
//        } catch (IOException e) {
//            properties = null;
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_READ_FILE") + sqlQueryFileName, e);
//        }
//    }
//
//    public Properties getSqlProperties() {
//        return properties;
//    }
//}
