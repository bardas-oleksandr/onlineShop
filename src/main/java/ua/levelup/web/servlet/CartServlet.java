//package ua.levelup.web.servlet;
//
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.model.Cart;
//import ua.levelup.service.CartService;
//import ua.levelup.service.support.ServiceHolder;
//import ua.levelup.web.dto.view.CartViewDto;
//import ua.levelup.web.dto.view.ProductViewDto;
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
//import java.util.Map;
//
//@WebServlet(name = "cartServlet", urlPatterns = "/cart")
//public class CartServlet extends HttpServlet {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    private static final String SUCCESS_JSP = "success.jsp";
//    private static final String CART_VIEW_JSP = "cartview.jsp";
//    private static final String ERROR_JSP = "error.jsp";
//    private static final String METHOD = "_method";
//    private static final String PUT = "PUT";
//    private static final String DELETE = "DELETE";
//    private static final String PRODUCT_COUNT = "productCount";
//    private static final String PRODUCT_ID = "productId";
//    private static final String CART = "cart";
//    private static final String EXCEPTION = "exception";
//
//    private static CartService cartService = (CartService) ServiceHolder
//            .getService(ServiceHolder.CART_SERVICE);
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        if (!response.isCommitted()) {
//            try {
//                HttpSession session = request.getSession(true);
//                CartViewDto cartDto = (CartViewDto) session.getAttribute(CART);
//                if(cartDto != null){
//                    Map<Integer, ProductViewDto> map = cartService.retrieveCartProducts(cartDto);
//                    //cartDto.setProductDtoMap(map);
//                }
//                response.sendRedirect(CART_VIEW_JSP);
//            } catch (Exception e) {
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
//                String method = request.getParameter(METHOD);
//                switch (method) {
//                    case PUT:
//                        putIntoCart(request, response);
//                        break;
//                    case DELETE:
//                        flushCart(request, response);
//                        break;
//                    default:
//                    throw new ApplicationException(MessageContainer.getMessageProperties()
//                            .getProperty("UNSUPPORTED_SERVLET_METHOD") + method);
//                }
//            } catch (Exception e) {
//                request.setAttribute(EXCEPTION, e);
//                logger.error("Exception: " + e.getClass() +
//                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
//                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
//            }
//        }
//    }
//
//    private static void putIntoCart(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//        Integer productId = Integer.parseInt(request.getParameter(PRODUCT_ID));
//        Integer productCount = Integer.parseInt(request.getParameter(PRODUCT_COUNT));
//        HttpSession session = request.getSession(true);
//        Cart cart = (Cart) session.getAttribute(CART);
//        if (cart == null) {
//            cart = new Cart();
//            session.setAttribute(CART, cart);
//        }
//        cartService.putIntoCart(cart, productId, productCount);
//        response.sendRedirect(SUCCESS_JSP);
//    }
//
//    private static void flushCart(HttpServletRequest request, HttpServletResponse response)
//            throws IOException {
//        HttpSession session = request.getSession(true);
//        session.removeAttribute(CART);
//        response.sendRedirect(SUCCESS_JSP);
//    }
//}
