package ua.levelup.dao.support;

import ua.levelup.config.PropertiesManager;
import org.h2.tools.RunScript;
import org.h2.tools.Server;

import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum DBManager {
    ;

    private static final String DRIVER = PropertiesManager.getApplicationProperties()
            .getProperty("test.driver");
    private static final String URL = PropertiesManager.getApplicationProperties()
            .getProperty("test.url");
    private static final String USER = PropertiesManager.getApplicationProperties()
            .getProperty("test.username");
    private static final String PASSWORD = PropertiesManager.getApplicationProperties()
            .getProperty("test.password");
    private static final String SCHEMA_INIT_SCRIPT = "schema_init.sql";
    private static final String SCHEMA_DESTROY_SCRIPT = "schema_destroy.sql";
    private static final String SCHEMA_CLEAN_SCRIPT = "schema_clean.sql";

    public static void create(){
        destroy();
        runScript(SCHEMA_INIT_SCRIPT);
    }

    public static void destroy(){
        runScript(SCHEMA_DESTROY_SCRIPT);
    }

    public static void clean(){
        runScript(SCHEMA_CLEAN_SCRIPT);
    }

    private static void runScript(String script){
        try {
            Class.forName(DRIVER);
            try (Connection connection = getConnection()) {
                Server server = Server.createTcpServer("-tcpAllowOthers").start();
                try (InputStreamReader reader = new InputStreamReader(Thread
                        .currentThread().getContextClassLoader()
                        .getResourceAsStream(script))) {
                    RunScript.execute(connection, reader);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException{
        Class.forName(DRIVER);
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
