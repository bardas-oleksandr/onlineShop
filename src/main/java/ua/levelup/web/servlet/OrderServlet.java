package ua.levelup.web.servlet;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.model.User;
import ua.levelup.model.support.OrderState;
import ua.levelup.service.CartService;
import ua.levelup.service.OrderService;
import ua.levelup.service.support.ServiceHolder;
import ua.levelup.web.dto.view.OrderViewDto;
import ua.levelup.web.dto.view.UserViewDto;
import ua.levelup.web.servlet.support.ServletUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

@WebServlet(name = "orderServlet", urlPatterns = "/order/*")
public class OrderServlet extends HttpServlet {

    private static final Logger logger = LogManager.getLogger();

    private static CartService cartService = (CartService) ServiceHolder.getService(ServiceHolder.CART_SERVICE);
    private OrderService orderService = (OrderService) ServiceHolder.getService(ServiceHolder.ORDER_SERVICE);

    private static final String ORDERS_VIEW_JSP = "ordersview.jsp";
    private static final String MANAGE_ORDERS_JSP = "manageorders.jsp";
    private static final String ERROR_JSP = "error.jsp";
    private static final String PROFILE_JSP = "profile.jsp";
    private static final String SERVLET_PATH = "/order";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String GET_FOR_USER = "GET_FOR_USER";
    private static final String GET_ALL = "GET_ALL";
    private static final String METHOD = "_method";
    private static final String ORDER_STATE = "orderStatus";
    private static final String ORDER_DATE = "orderDate";
    private static final String ADDRESS = "address";
    private static final String PAYMENT_CONDITIONS = "paymentConditions";
    private static final String CART = "cart";
    private static final String USER = "user";
    private static final String USER_ID = "userId";
    private static final String ORDER_LIST = "orderList";
    private static final String EXCEPTION = "exception";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!response.isCommitted()) {
            try {
                String method = request.getParameter(METHOD);
                switch (method){
                    case GET_FOR_USER:{
                        HttpSession session = request.getSession(true);
                        UserViewDto user = (UserViewDto) session.getAttribute(USER);
                        if(user != null){
                            List<OrderViewDto> orderViewDtoList = orderService.getUsersOrders(user.getId());
                            session.setAttribute(ORDER_LIST, orderViewDtoList);
                        }
                        response.sendRedirect(ORDERS_VIEW_JSP);
                    }
                    break;
                    case GET_ALL:{
                        HttpSession session = request.getSession(true);
                        setOrderListAttribute(session);
                        response.sendRedirect(MANAGE_ORDERS_JSP);
                    }
                    break;
                    default:
                        throw new ApplicationException(MessageHolder.getMessageProperties()
                                .getProperty("UNSUPPORTED_SERVLET_METHOD") + method);
                }

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
                String method = request.getParameter(METHOD);
                switch (method) {
                    case POST:
                        confirmOrder(request);
                        response.sendRedirect(PROFILE_JSP);
                        break;
                    case PUT:
                        updateOrder(request);
                        setOrderListAttribute(request.getSession(true));
                        String redirectURL = ServletUtils.getUrlForRedirect(request, SERVLET_PATH);
                        response.sendRedirect(redirectURL + "/" + MANAGE_ORDERS_JSP);
                        break;
                    default:
                        throw new ApplicationException(MessageHolder.getMessageProperties()
                                .getProperty("UNSUPPORTED_SERVLET_METHOD") + method);
                }
            } catch (Exception e) {
                request.setAttribute(EXCEPTION, e);
                logger.error("Exception: " + e.getClass() +
                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
            }
        }
    }

    private void confirmOrder(HttpServletRequest request)
            throws ServletException, IOException {
        Order order = extractOrderWithOrderPositions(request);
        orderService.createOrder(order);
        HttpSession session = request.getSession(true);
        session.removeAttribute(CART);
    }

    private void updateOrder(HttpServletRequest request) throws IOException {
        int orderId = ServletUtils.retrieveEntityId(request);
        Order order = extractOrderForUpdate(request);
        orderService.updateOrder(order, orderId);
    }

    private Order extractOrderWithOrderPositions(HttpServletRequest request) {
        Order order = extractNewOrder(request);
        List<OrderPosition> orderPositionCreateDtoList = extractOrderPositions(request);
        order.setOrderPositionList(orderPositionCreateDtoList);
        return order;
    }

    private Order extractOrderForUpdate(HttpServletRequest request) {

        //Вероятно тут надо вытащить юзера из сессии
        Integer userId = Integer.parseInt(request.getParameter(USER_ID));
        User user = new User();


        Integer orderState = Integer.parseInt(request.getParameter(ORDER_STATE));
        String address = request.getParameter(ADDRESS);
        Integer paymentConditions = Integer.parseInt(request.getParameter(PAYMENT_CONDITIONS));
        Timestamp date = Timestamp.valueOf(request.getParameter(ORDER_DATE));
        return new Order(user, orderState, date, address, paymentConditions);
    }

    private Order extractNewOrder(HttpServletRequest request) {

        //В сессии у меня userDto а мне надо user.
        HttpSession session = request.getSession(true);
        UserViewDto userDto = (UserViewDto) session.getAttribute(USER);
        User user = new User();

        Integer orderState = OrderState.REGISTERED.ordinal();
        String address = request.getParameter(ADDRESS);
        Integer paymentConditions = Integer.parseInt(request.getParameter(PAYMENT_CONDITIONS));
        Timestamp date = new Timestamp(System.currentTimeMillis());
        return new Order(user, orderState, date, address, paymentConditions);
    }

    private List<OrderPosition> extractOrderPositions(HttpServletRequest request) {
//        HttpSession session = request.getSession(true);
//        Cart cart = (Cart) session.getAttribute(CART);
//        Map<Integer, Integer> productCountMap = cart.getProductCountMap();
//        Map<Integer, ProductDto> productDtoMap = cartService.retrieveCartProducts(cart);
//        Set<Integer> productIdSet = productCountMap.keySet();
//        List<OrderPosition> list = new ArrayList<>();
//        for (Integer productId : productIdSet) {
//            int count = productCountMap.get(productId);
//            float price = productDtoMap.get(productId).getPrice();
//
//
//            list.add(new OrderPosition(productId, count, price));
//        }
//        return list;

        return null;
    }

    private void setOrderListAttribute(HttpSession session){
        List<OrderViewDto> orderViewDtoList = orderService.getAllOrders();
        session.setAttribute(ORDER_LIST, orderViewDtoList);
    }
}
