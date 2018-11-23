package ua.levelup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.util.List;
import java.util.Locale;

/**
 * Конфигурация веб-контекста для сервлета диспетчера
 * Автор: Бардась А. А.
 */
//1.@EnableWebMvc При xml-конфигурировании вместо @EnableWebMvc мы должны были бы вставить строку
// <mvc:annotation-driven />
//в файл корневого контекста веб-приложения
//@ComponentScan("ua.levelup") - указываем где искать классы с аннотациями для конфигурирования контекста
@Configuration
@EnableWebMvc
@ComponentScan("ua.levelup.controller")
@ComponentScan("ua.levelup.exception")
public class WebMvcConfig implements WebMvcConfigurer {

    //Продолжительность (в секундах) нахождения ресурсов в кеше
    private static final int CASH_PERIOD = 86400; // 24*60*60

    //Распознаватель представлений
    //Контроллеры возвращают имена представлений, распознаватель определяет какому именно файлу представления
    //соответствует имя, возвращенное контроллером
    //Вместо определения бина распознавателя мы переопределили ниже метод configureViewResolvers
//    @Bean(name="viewResolver")
//    public InternalResourceViewResolver internalResourceViewResolver(){
//        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
//        viewResolver.setPrefix("/WEB-INF/views/");
//        viewResolver.setSuffix(".jsp");
//        return viewResolver;
//    }

    //Конфигурирование распознавателя представлений
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/WEB-INF/views/", ".jsp");
    }

    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {

    }

    //-------------Настройка доступа к статическим ресурсам------------------
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //URL ресурса = конекст приложения + /resources/css/**
        //Искать эти ресрсы следует по адресу /resources/css/
        registry.addResourceHandler("/resources/css/**")
                .addResourceLocations("/resources/css/").setCachePeriod(CASH_PERIOD);
    }

    //с. 646 "Spring для профессионалов"
    //Перенаправляет все запросы на статические ресурсы с DispatcherServlet
    //на сервлет контейнера, установленный по умолчанию. для лучшей производительности
    //Это также можно задать в xml-конфигурации <default-servlet-handler/>
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    //-------------Настройка локализации------------------------------------------
    //Конфигурирование перехватчика, отвечающего за локализацию.
    //1.Перехватчик будет следить за параметром "lang",
    //передаваемом в запросах к сервлету диспетчера
    //и будет изменять свойства бина ReloadableResourceBundleMessageSource
    //2.Добавлять перехватчик надо именно через метод создания бина или через ссылку на бин.
    //Если перехватчик будет создан прямо тут и тут же добавлен в регистр - работать ничего не будет
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }

    @Bean("localeChangeInterceptor")
    public LocaleChangeInterceptor localeChangeInterceptor(){
        LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
        localeChangeInterceptor.setParamName("lang");
        return localeChangeInterceptor;
    }

    //Согласно с.635, рис. 16.2 локализация должна конфигурироваться в конексте сервлета диспетчера.
    //Однако, если выполняется не xml-конфигурирование а конфигурирование с использованием
    //классов конфигурации (как в нашем случае), то бины для поддержки локализации должны определяться
    //в корневом конексте, то есть - в текушем классе.
    //Бин, который загружает сообщения из файлов поддержки локализации
    @Bean("messageSource")
    public ReloadableResourceBundleMessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("WEB-INF/i18n/application", "WEB-INF/i18n/message"
                , "WEB-INF/i18n/validation");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setUseCodeAsDefaultMessage(true);
        //с. 647 "Spring для профессионалов"
        //Указывает что если файлы для требуемой локали не были найдены,
        //то возвращаться к локали, в которой работает система, не нужно.
        messageSource.setFallbackToSystemLocale(false);
        return messageSource;
    }

    //с. 647 "Spring для профессионалов"
    //Бин поддерживает хранение и извлечение локали из cookie пользовательского браузера.
    @Bean("localeResolver")
    public CookieLocaleResolver cookieLocaleResolver() {
        CookieLocaleResolver cookieLocaleResolver = new CookieLocaleResolver();
        cookieLocaleResolver.setCookieName("locale");
        cookieLocaleResolver.setDefaultLocale(new Locale("ua","UA"));
        cookieLocaleResolver.setCookieMaxAge(1440*31);  //срок жизни куки - 1 месяц
        return cookieLocaleResolver;
    }
}