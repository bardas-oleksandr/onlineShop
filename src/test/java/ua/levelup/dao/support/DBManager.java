//package ua.levelup.dao.support;
//
//import org.h2.tools.RunScript;
//import org.h2.tools.Server;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.Properties;
//
//public class DBManager {
//
//    @Autowired
//    private Properties applicationProperties;
//
//    private final String DRIVER = applicationProperties.getProperty("test.driver");
//    private final String URL = applicationProperties.getProperty("test.url");
//    private final String USER = applicationProperties.getProperty("test.username");
//    private final String PASSWORD = applicationProperties.getProperty("test.password");
//    private static final String SCHEMA_INIT_SCRIPT = "schema_init.sql";
//    private static final String SCHEMA_DESTROY_SCRIPT = "schema_destroy.sql";
//    private static final String SCHEMA_CLEAN_SCRIPT = "schema_clean.sql";
//
//    public void create(){
//        destroy();
//        runScript(SCHEMA_INIT_SCRIPT);
//    }
//
//    public void destroy(){
//        runScript(SCHEMA_DESTROY_SCRIPT);
//    }
//
//    public void clean(){
//        runScript(SCHEMA_CLEAN_SCRIPT);
//    }
//
//    private void runScript(String script){
//        try {
//            Class.forName(DRIVER);
//            try (Connection connection = getConnection()) {
//                Server server = Server.createTcpServer("-tcpAllowOthers").start();
//                try (InputStreamReader reader = new InputStreamReader(Thread
//                        .currentThread().getContextClassLoader()
//                        .getResourceAsStream(script))) {
//                    RunScript.execute(connection, reader);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public Connection getConnection() throws ClassNotFoundException, SQLException{
//        Class.forName(DRIVER);
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
//}
