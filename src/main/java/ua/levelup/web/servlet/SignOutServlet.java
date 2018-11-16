//package ua.levelup.web.servlet;
//
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import java.io.IOException;
//
//@WebServlet(name = "signOutServlet", urlPatterns = "/logout")
//public class SignOutServlet extends HttpServlet {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    private static final String INDEX_JSP = "index.jsp";
//    private static final String ERROR_JSP = "error.jsp";
//    private static final String CART = "cart";
//    private static final String USER = "user";
//    private static final String EXCEPTION = "exception";
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        if (!response.isCommitted()) {
//            try{
//                request.getRequestDispatcher("/").forward(request, response);
//            }catch (Exception e) {
//                request.setAttribute(EXCEPTION, e);
//                logger.error("Exception: " + e.getClass() +
//                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
//                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
//            }
//        }
//    }
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        if (!response.isCommitted()) {
//            try {
//                HttpSession session = request.getSession(true);
//                session.removeAttribute(USER);
//                session.removeAttribute(CART);
//                response.sendRedirect(INDEX_JSP);
//            } catch (Exception e) {
//                request.setAttribute(EXCEPTION, e);
//                logger.error("Exception: " + e.getClass() +
//                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
//                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
//            }
//        }
//    }
//}
