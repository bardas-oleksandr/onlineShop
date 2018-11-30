package ua.levelup.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;

/**
 * Класс WebAppInitializer предназначен для инициализации веб приложения
 * Автор: Бардась А. А.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        AnnotationConfigWebApplicationContext appRootContext = new AnnotationConfigWebApplicationContext();
        appRootContext.register(ApplicationConfig.class);
        appRootContext.register(SecurityConfig.class);

        servletContext.addListener(new ContextLoaderListener(appRootContext));

        AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
        dispatcherServletContext.register(WebMvcConfig.class);

        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcher"
                , new DispatcherServlet(dispatcherServletContext));
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/", "/index");

        servletContext.setInitParameter("defaultHtmlEscape", "true");

        FilterRegistration.Dynamic securityFilterReg = servletContext
                .addFilter("springSecurityFilterChain", DelegatingFilterProxy.class);

        securityFilterReg.addMappingForUrlPatterns(null, false, "/*");
    }
}