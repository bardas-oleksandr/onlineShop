package ua.levelup.web.filter;

import ua.levelup.config.SecurityConfig;
import ua.levelup.model.support.UserState;
import ua.levelup.web.dto.UserViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Set;

public class SecurityFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private static final String USER = "user";
    private static final String CONTEXT = "/onlineShop/";
    private static final String ERROR_JSP = "error.jsp";
    private static final String PROFILE_JSP = "profile.jsp";
    private static final String LOGIN_FORM_JSP = "loginform.jsp";
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
            String url = httpServletRequest.getRequestURL().toString();
            HttpSession session = httpServletRequest.getSession();
            if (shouldBeProtected(url)) {
                if (session == null || session.getAttribute(USER) == null) {
                    logger.info("URL " + url + " was protected from unsigned user");
                    httpServletResponse.sendRedirect(CONTEXT + LOGIN_FORM_JSP);
                }
            } else if (session != null && session.getAttribute(USER) != null) {
                UserViewDto user = (UserViewDto) session.getAttribute(USER);
                if (shouldBeProtectedFromBlocked(url) && UserState.BLOCKED.ordinal() == user.getState()) {
                    logger.info("URL " + url + " was protected from blocked user");
                    httpServletResponse.sendRedirect(CONTEXT + PROFILE_JSP);
                } else if (shouldBeProtectedFromActive(url) && UserState.ACTIVE.ordinal() == user.getState()) {
                    logger.info("URL " + url + " was protected from user");
                    httpServletResponse.sendRedirect(CONTEXT);
                } else if (shouldBeProtectedFromAdmin(url) && UserState.ADMIN.ordinal() == user.getState()) {
                    logger.info("URL " + url + " was protected from administrator");
                    httpServletResponse.sendRedirect(CONTEXT);
                }
            }
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

    private boolean shouldBeProtected(String url) {
        Set<String> urls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.ALL);
        return urls.stream().anyMatch(url::endsWith);
    }

    private boolean shouldBeProtectedFromAdmin(String url) {
        Set<String> urls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.ADMIN);
        return urls.stream().anyMatch(url::endsWith);
    }

    private boolean shouldBeProtectedFromActive(String url) {
        Set<String> urls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.ACTIVE);
        return urls.stream().anyMatch(url::endsWith);
    }

    private boolean shouldBeProtectedFromBlocked(String url) {
        Set<String> urls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.BLOCKED);
        return urls.stream().anyMatch(url::contains);
    }
}
