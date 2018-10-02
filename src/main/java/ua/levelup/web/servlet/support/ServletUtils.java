package ua.levelup.web.servlet.support;

import ua.levelup.dao.support.OrderMethod;
import ua.levelup.model.Product;

import javax.servlet.http.HttpServletRequest;

public class ServletUtils {

    private static final int DEFAULT_CATEGORY_ID = 0;
    private static final int DEFAULT_SUBCATEGORY_ID = 0;
    private static final int DEFAULT_MANUFACTURER_ID = 0;
    private static final boolean DEFAULT_PRODUCT_AVAILABILITY = true;
    private static final float DEFAULT_MIN_PRICE = 0.0f;
    private static final float DEFAULT_MAXPRICE = 1000.0f;
    private static final OrderMethod DEFAULT_ORDER_METHOD = OrderMethod.CHEAP_FIRST;

    public static int retrieveEntityId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo.startsWith("/")) {
            pathInfo = pathInfo.substring(1);
        }
        return Integer.parseInt(pathInfo);
    }

    public static String getUrlForRedirect(HttpServletRequest request, String servletPath) {
        String requestURL = request.getRequestURL().toString();
        String pathInfo = request.getPathInfo();
        if (pathInfo!= null && requestURL.endsWith(pathInfo)) {
            requestURL = requestURL.substring(0, requestURL.length() - pathInfo.length());
        }
        if (requestURL.endsWith(servletPath)) {
            requestURL = requestURL.substring(0, requestURL.length() - servletPath.length());
        }
        return requestURL;
    }

    public static SearchParams getDefaultSearchParams(){
        Product product = new Product();


        //product.setCategoryId(DEFAULT_CATEGORY_ID);
        //product.setManufacturerId(DEFAULT_MANUFACTURER_ID);



        product.setAvailable(DEFAULT_PRODUCT_AVAILABILITY);
        return new SearchParams(product, DEFAULT_CATEGORY_ID, DEFAULT_SUBCATEGORY_ID,
                DEFAULT_MIN_PRICE, DEFAULT_MAXPRICE, DEFAULT_ORDER_METHOD);
    }
}
