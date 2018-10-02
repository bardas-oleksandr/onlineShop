package ua.levelup.web.servlet;

import ua.levelup.dao.support.OrderMethod;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Product;
import ua.levelup.service.ProductService;
import ua.levelup.service.support.ServiceHolder;
import ua.levelup.web.servlet.support.SearchParams;
import ua.levelup.web.dto.ProductDto;
import ua.levelup.web.servlet.support.ServletUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "productServlet", urlPatterns = "/product/*")
public class ProductServlet extends HttpServlet {

    private static final String INDEX_JSP = "index.jsp";
    private static final String ERROR_JSP = "error.jsp";
    private static final String SUCCESS_JSP = "success.jsp";
    private static final String POST = "POST";
    private static final String PUT = "PUT";
    private static final String RESET = "RESET";
    private static final String DELETE = "DELETE";
    private static final String SERVLET_PATH = "/product";
    private static final String PRODUCT_LIST = "productList";
    private static final String EXCEPTION = "exception";
    private static final String METHOD = "_method";
    private static final String MIN_PRICE = "minPrice";
    private static final String MAX_PRICE = "maxPrice";
    private static final String PRICE = "price";
    private static final String SORT_BY_PARAM = "sortByParam";
    private static final String PRODUCT_NAME = "productName";
    private static final String SUBCATEGORY_ID = "subcategoryId";
    private static final String CATEGORY_ID = "categoryId";
    private static final String MANUFACTURER_ID = "manufacturerId";
    private static final String AVAILABLE = "available";
    private static final String DESCRIPTION = "productDescription";
    private static final String SEARCH_PARAMS = "searchParams";

    private static final Logger logger = LogManager.getLogger();

    private ProductService productService = (ProductService) ServiceHolder
            .getService(ServiceHolder.PRODUCT_SERVICE);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        if (!response.isCommitted()) {
            try {
                String method = request.getParameter(METHOD);
                SearchParams searchParams;
                if (method != null && method.equals(RESET)) {
                    searchParams = ServletUtils.getDefaultSearchParams();
                } else {
                    searchParams = extractSearchParams(request);
                }
                HttpSession session = request.getSession(true);
                productSearch(session, searchParams);
                response.sendRedirect(INDEX_JSP);
            } catch (Exception e) {
                request.setAttribute(EXCEPTION, e);
                logger.error("Exception: " + e.getClass() +
                        "\tMessage: " + e.getMessage() + "\tCause:" + e.getCause());
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
                        createNewProduct(request);
                        break;
                    case DELETE:
                        deleteProduct(request);
                        break;
                    case PUT:
                        updateProduct(request);
                        break;
                    default:
                        throw new ApplicationException(MessageHolder.getMessageProperties()
                                .getProperty("UNSUPPORTED_SERVLET_METHOD") + method);
                }

                HttpSession session = request.getSession(true);
                SearchParams searchParams = (SearchParams) session.getAttribute(SEARCH_PARAMS);
                if (searchParams == null) {
                    searchParams = ServletUtils.getDefaultSearchParams();
                }
                productSearch(session, searchParams);

                switch (method) {
                    case POST:
                        response.sendRedirect(SUCCESS_JSP);
                        break;
                    case DELETE:
                    case PUT:
                        String redirectURL = ServletUtils.getUrlForRedirect(request, SERVLET_PATH);
                        response.sendRedirect(redirectURL + "/" + INDEX_JSP);
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

    private void updateProduct(HttpServletRequest request) throws ServletException, IOException {
        int productId = ServletUtils.retrieveEntityId(request);
        Product product = extractNewProduct(request);
        productService.updateProduct(product, productId);
    }

    private void deleteProduct(HttpServletRequest request) throws ServletException, IOException {
        int productId = ServletUtils.retrieveEntityId(request);
        productService.deleteProduct(productId);
    }

    private void createNewProduct(HttpServletRequest request) throws IOException {
        Product product = extractNewProduct(request);
        productService.createNewProduct(product);
    }

    private SearchParams extractSearchParams(HttpServletRequest request) {
        int categoryId = Integer.parseInt(request.getParameter(CATEGORY_ID));
        int subcategoryId = Integer.parseInt(request.getParameter(SUBCATEGORY_ID));
        int actualSearchedCategoryId = (subcategoryId > 0) ? subcategoryId : categoryId;
        float minPrice = Float.parseFloat(request.getParameter(MIN_PRICE));
        float maxPrice = Float.parseFloat(request.getParameter(MAX_PRICE));
        short orderMethod = Short.parseShort(request.getParameter(SORT_BY_PARAM));
        Product product = new Product();


//        product.setCategoryId(actualSearchedCategoryId);
//        int manufacturerId = Integer.parseInt(request.getParameter(MANUFACTURER_ID));
//        product.setManufacturerId(manufacturerId);



        boolean available = request.getParameter(AVAILABLE) != null;    //Checkbox can equals either "on" or null
        product.setAvailable(available);
        return new SearchParams(product, categoryId, subcategoryId,
                minPrice, maxPrice, OrderMethod.getOrderMethod(orderMethod));
    }

    private Product extractNewProduct(HttpServletRequest request) {
        String productName = request.getParameter(PRODUCT_NAME);
        String stringPrice = request.getParameter(PRICE);
        float price = StringUtils.isEmpty(stringPrice) ? 0 : Float.parseFloat(stringPrice);
        boolean available = request.getParameter(AVAILABLE) != null;
        String description = request.getParameter(DESCRIPTION);
        int categoryId = Integer.parseInt(request.getParameter(CATEGORY_ID));
        int manufacturerId = Integer.parseInt(request.getParameter(MANUFACTURER_ID));


        //return new Product(productName, price, available, description, categoryId, manufacturerId);
        return null;
    }

    private void productSearch(HttpSession session, SearchParams searchParams) {
        List<ProductDto> productList = productService.searchProducts(searchParams);
        session.setAttribute(PRODUCT_LIST, productList);
        session.setAttribute(SEARCH_PARAMS, searchParams);
    }
}