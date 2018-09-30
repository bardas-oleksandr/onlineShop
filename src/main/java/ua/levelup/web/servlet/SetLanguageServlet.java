package ua.levelup.web.servlet;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.util.LocalizationSupport;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Locale;

@WebServlet(name = "setLanguageServlet", urlPatterns = "/setlanguage")
public class SetLanguageServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final String INDEX_JSP = "index.jsp";
    private static final String ERROR_JSP = "error.jsp";
    private static final String COOKIE_LANGUAGE_NAME = "language";
    private static final String LOCALE = "locale";
    private static final String EXCEPTION = "exception";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!response.isCommitted()) {
            try{
                request.getRequestDispatcher("/").forward(request, response);
            }catch (Exception e) {
                request.setAttribute(EXCEPTION, e);
                logger.error("Exception: " + e.getClass() +
                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!response.isCommitted()) {
            try{
                HttpSession session = request.getSession(true);
                Locale locale = (Locale)session.getAttribute(LOCALE);
                Locale newLocale = LocalizationSupport.switchLocale(locale);
                session.setAttribute(LOCALE, newLocale);
                LocalizationSupport.createAndAddCookieToResponse(COOKIE_LANGUAGE_NAME,
                        newLocale.getLanguage(), response);
                if(locale != null){
                    logger.info("Language was changed: " + newLocale.getLanguage());
                    response.sendRedirect(INDEX_JSP);
                }else{
                    logger.error("Language was not changed correctly: session attribute " +
                            "\'locale\' is not initialized");
                    throw new ApplicationException(MessageHolder.getMessageProperties()
                            .getProperty("FAILED_CHANGE_LANGUAGE"));
                }
            }catch(Exception e){
                request.setAttribute(EXCEPTION,e);
                logger.error("Exception: " + e.getClass() +
                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
            }
        }
    }
}
