//package ua.levelup.dao.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import ua.levelup.config.ConnectionPoolConfig;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.Connection;
//
//public abstract class AbstractDaoImpl {
//
//    private static final Logger logger = LogManager.getLogger();
//    private static final int MAX_WAIT = 3000;
//
//    protected Connection connection;
//
//    @Autowired
//    private MessageContainer messageContainer;
//
//    public AbstractDaoImpl() {
//        try{
//            //this.connection = ConnectionPoolConfig.getConnectionPool().borrowObject(MAX_WAIT);
//        }catch (Exception e){
//            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_RETRIEVE_CONNECTION"));
//        }
//    }
//
//    public AbstractDaoImpl(Connection connection){
//        this.connection = connection;
//    }
//}
