//package ua.levelup.mapper;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//public abstract class AbstractMapper<T> {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    @Autowired
//    private MessageContainer messageContainer;
//
//    public T mapRow(ResultSet resultSet) throws ApplicationException {
//        List<T> list =  mapAllRows(resultSet);
//        if(list.size() > 1){
//            logger.error(messageContainer.getMessageProperties().getProperty("AMBIGUOUS_RESULTSET"));
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("AMBIGUOUS_RESULTSET"));
//        }
//        if(list.size() == 0){
//            logger.error(messageContainer.getMessageProperties().getProperty("EMPTY_RESULTSET"));
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("EMPTY_RESULTSET"));
//        }
//        return list.get(0);
//    }
//
//    public List<T> mapAllRows(ResultSet resultSet) throws ApplicationException{
//        try{
//            List<T> list = new ArrayList<>();
//            while(resultSet.next()){
//                T object = getObject(resultSet);
//                list.add(object);
//            }
//            return list;
//        }catch (NullPointerException | SQLException e){
//            logger.error(messageContainer.getMessageProperties().getProperty("FAILED_MAP_RESULTSET"));
//            throw new ApplicationException(messageContainer.getMessageProperties()
//                    .getProperty("FAILED_MAP_RESULTSET"), e);
//        }
//    }
//
//    protected abstract T getObject(ResultSet resultSet) throws SQLException;
//}
