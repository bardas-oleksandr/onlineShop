package ua.levelup.web.servlet;

import ua.levelup.model.User;
import ua.levelup.model.support.UserState;
import ua.levelup.service.support.ServiceHolder;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet(name = "registrationServlet", urlPatterns = "/register")
public class RegistrationServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static final String ERROR_JSP = "error.jsp";
    private static final String PROFILE_JSP = "profile.jsp";
    private static final String USER = "user";
    private static final String USER_NAME = "user_name";
    private static final String USER_EMAIL = "user_email";
    private static final String USER_PASSWORD = "user_password";
    private static final String EXCEPTION = "exception";

    private UserService userService = (UserService) ServiceHolder.getService(ServiceHolder.USER_SERVICE);

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
            try {
                User user = extractUser(request);
                UserDto userDto = userService.registerUser(user);
                HttpSession session = request.getSession(true);
                session.setAttribute(USER, userDto);
                response.sendRedirect(PROFILE_JSP);
            }
            catch(Exception e){
                request.setAttribute(EXCEPTION,e);
                logger.error("Exception: " + e.getClass() +
                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
            }
        }
    }

    private User extractUser(HttpServletRequest request) {
        String userName = request.getParameter(USER_NAME);
        String password = request.getParameter(USER_PASSWORD);
        String email = request.getParameter(USER_EMAIL);
        UserState state = UserState.ACTIVE;
        return new User(userName, password, email, state.ordinal());
    }
}
