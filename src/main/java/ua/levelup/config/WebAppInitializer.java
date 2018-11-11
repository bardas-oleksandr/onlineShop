package ua.levelup.config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**Класс WebAppInitializer предназначен для инициализации веб приложения
 *Автор: Бардась А. А.
 */
public class WebAppInitializer implements WebApplicationInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        //Создаем корневой контекст веб-приложения
        //При xml-конфигурировании вместо этого действия нам бы пришлось создавать файл типа "root-context.xml"
        //И прописывать в web.xml следующее
        //<context-param>
        //    <param-name>contextConfigLocation</param-name>
        //    <param-value>/WEB-INF/spring/root-context.xml</param-value>
        //</context-param>
        //Теперь же роль файла root-context.xml исполняет класс ApplicationConfig.class
        AnnotationConfigWebApplicationContext appRootContext = new AnnotationConfigWebApplicationContext();
        appRootContext.register(ApplicationConfig.class);

        //Добавляем прослушивателя событий для управления жизненным циклом корневого контекста веб-приложения
        //Это также можно было выполнить в web.xml
        //<listener>
        //  <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
        //</listener>
        servletContext.addListener(new ContextLoaderListener(appRootContext));

        //Создание контекста для сервлета диспетчера
        //При xml-конфигурировании вместо этого шага мы должны были бы прописать в web.xml что-то типа
        //<servlet>
	    //    <servlet-name>myServlet</servlet-name>
	    //    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
        //        <init-param>
        //            <param-name>contextConfigLocation</param-name>
        //            <param-value>/WEB-INF/spring/servlet-context.xml</param-value>
        //        </init-param>
	    //    <load-on-startup>1</load-on-startup>
        //</servlet>
        //Теперь же роль файла servlet-context.xml исполняет класс WebMvcConfig.class
        AnnotationConfigWebApplicationContext dispatcherServletContext = new AnnotationConfigWebApplicationContext();
        dispatcherServletContext.register(WebMvcConfig.class);

        //Регистрация сервлета диспетчера
        ServletRegistration.Dynamic dispatcherServlet = servletContext.addServlet("dispatcher"
                , new DispatcherServlet(dispatcherServletContext));
        dispatcherServlet.setLoadOnStartup(1);
        dispatcherServlet.addMapping("/","/index");

        //Установка параметров контекста (контейнера)
        //То же самое можно задавать в xml-конфигурации
        //<context-param>
        //    <param-name>defaultHtmlEscape</param-name>
        //    <param-value>true</param-value>
        //</context-param>
        servletContext.setInitParameter("defaultHtmlEscape","true");

        //Регистрация других сервлетов и фильтров по необходимости

    }
}