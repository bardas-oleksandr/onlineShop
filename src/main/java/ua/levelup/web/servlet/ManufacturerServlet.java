//package ua.levelup.web.servlet;
//
//import ua.levelup.model.Manufacturer;
//import ua.levelup.service.ManufacturerService;
//import ua.levelup.service.support.ServiceHolder;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
//@WebServlet(name = "manufacturerServlet", urlPatterns = "/manufacturer")
//public class ManufacturerServlet extends HttpServlet {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    private static final String SUCCESS_JSP = "success.jsp";
//    private static final String ERROR_JSP = "error.jsp";
//    private static final String MANUFACTURER_NAME = "manufacturerName";
//    private static final String EXCEPTION = "exception";
//
//    private ManufacturerService manufacturerService = (ManufacturerService) ServiceHolder
//            .getService(ServiceHolder.MANUFACTURER_SERVICE);
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        if (!response.isCommitted()){
//            try{
//                Manufacturer manufacturer = extractNewManufacturer(request);
//                manufacturerService.createNewManufacturer(manufacturer);
//                response.sendRedirect(SUCCESS_JSP);
//            }catch(Exception e){
//                request.setAttribute(EXCEPTION, e);
//                logger.error("Exception: " + e.getClass() +
//                        "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
//                request.getRequestDispatcher(ERROR_JSP).forward(request, response);
//            }
//        }
//    }
//
//    private Manufacturer extractNewManufacturer(HttpServletRequest request){
//        String manufacturerName = request.getParameter(MANUFACTURER_NAME);
//        return new Manufacturer(manufacturerName);
//    }
//}