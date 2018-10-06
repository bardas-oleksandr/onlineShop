package ua.levelup.web.servlet;

import ua.levelup.service.support.ServiceHolder;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.view.UserViewDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "loginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final String PROFILE_JSP = "profile.jsp";
    private static final String ERROR_JSP = "error.jsp";
    private static final String USER = "user";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String EXCEPTION = "exception";

    private UserService userService = (UserService) ServiceHolder.getService(ServiceHolder.USER_SERVICE);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!response.isCommitted()) {
            try {

                request.getRequestDispatcher("/").forward(request, response);
            } catch (Exception e) {
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
            try {
                String email = request.getParameter(USER_EMAIL);
                String password = request.getParameter(USER_PASSWORD);
                UserViewDto user = userService.login(email, password);
                HttpSession session = request.getSession(true);
                session.setAttribute(USER, user);
                response.sendRedirect(PROFILE_JSP);
            } catch (Exception e) {
                request.setAttribute(EXCEPTION, e);
                logger.error("Exception: " + e.getClass() +
                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
            }
        }
    }
}