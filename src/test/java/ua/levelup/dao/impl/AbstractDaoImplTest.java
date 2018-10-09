//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.support.DBManager;
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.BeforeClass;
//
//import java.sql.Connection;
//import java.sql.SQLException;
//
//public class AbstractDaoImplTest {
//
//    private Connection connection;
//
//    AbstractDaoImplTest(){
//        try{
//            connection = DBManager.getConnection();
//        }catch (ClassNotFoundException | SQLException e){
//            e.printStackTrace();
//        }
//    }
//
//    @BeforeClass
//    public static void initDatabase(){
//        DBManager.create();
//    }
//
//    @AfterClass
//    public static void destroy(){
//        DBManager.destroy();
//    }
//
//    @After
//    public void deleteData(){
//        DBManager.clean();
//        try{
//            if(!connection.isClosed()){
//                connection.close();
//            }
//        }catch (SQLException e){
//            e.printStackTrace();
//        }
//    }
//
//    public Connection getConnection(){
//        return this.connection;
//    }
//}
