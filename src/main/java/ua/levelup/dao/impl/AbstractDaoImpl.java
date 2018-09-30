package ua.levelup.dao.impl;

import ua.levelup.config.ConnectionPoolHolder;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;

public abstract class AbstractDaoImpl {

    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_WAIT = 3000;

    protected Connection connection;

    public AbstractDaoImpl() {
        try{
            this.connection = ConnectionPoolHolder.getConnectionPool().borrowObject(MAX_WAIT);
        }catch (Exception e){
            logger.error("Message: " + e.getMessage() + "\tCause:" + e.getCause());
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("FAILED_RETRIEVE_CONNECTION"));
        }
    }

    public AbstractDaoImpl(Connection connection){
        this.connection = connection;
    }
}
