package ua.levelup.web.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.Locale;

public class ApplicationLifeCycleListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        Locale.setDefault(Locale.US);
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        //Nothing to destroy
    }
}
