package ua.levelup.web.filter;

import ua.levelup.util.LocalizationSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Locale;
import java.util.Optional;

public class LocalizationFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private static final String ERROR_JSP = "error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //nothing to init
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) request;
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            saveJSessionIdCookie(httpServletRequest, httpServletResponse);
            initLocalizationSettings(httpServletRequest, httpServletResponse);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            request.setAttribute(EXCEPTION, e);
            logger.error("Exception: " + e.getClass() +
                    "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
            request.getRequestDispatcher(ERROR_JSP).forward(request, response);
        }
    }

    @Override
    public void destroy() {
        //nothing to destroy
    }

    private void saveJSessionIdCookie(HttpServletRequest request, HttpServletResponse response) {
        Optional<Cookie> cookieOptional = LocalizationSupport
                .extractCookieFromRequest(request, "JSESSIONID");
        if (!cookieOptional.isPresent()) {
            logger.warn("JSESSIONID was not created");
        }else{
            Cookie cookie = cookieOptional.get();
            LocalizationSupport.addCookie(cookie,response);
        }
    }

    private void initLocalizationSettings(HttpServletRequest request, HttpServletResponse response){
        HttpSession session = request.getSession(true);
        Locale locale = (Locale)session.getAttribute("locale");
        if(locale == null){
            Optional<Cookie> cookieOptional = LocalizationSupport
                    .extractCookieFromRequest(request, "language");
            String language = null;
            if(cookieOptional.isPresent()){
                language = cookieOptional.get().getValue();
            }
            locale = LocalizationSupport.getLocale(language);
            session.setAttribute("locale", locale);
        }
    }
}
