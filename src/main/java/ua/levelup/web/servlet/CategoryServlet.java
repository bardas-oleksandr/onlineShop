//package ua.levelup.web.servlet;
//
//import ua.levelup.model.Category;
//import ua.levelup.service.CategoryService;
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
//@WebServlet(name = "categoryServlet", urlPatterns = "/category")
//public class CategoryServlet extends HttpServlet {
//
//    private static final Logger logger = LogManager.getLogger();
//
//    private static final String SUCCESS_JSP = "success.jsp";
//    private static final String ERROR_JSP = "error.jsp";
//    private static final String EXCEPTION = "exception";
//    private static final String CATEGORY_NAME = "categoryName";
//    private static final String PARENT_CATEGORY_ID = "categoryId";
//
//    private CategoryService categoryService = (CategoryService) ServiceHolder
//            .getService(ServiceHolder.CATEGORY_SERVICE);
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        if (!response.isCommitted()){
//            try{
//                Category category = extractNewCategory(request);
//                categoryService.createNewCategory(category);
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
//    private Category extractNewCategory(HttpServletRequest request){
//        String categoryName = request.getParameter(CATEGORY_NAME);
//        Integer parentCategoryId = Integer.parseInt(request.getParameter(PARENT_CATEGORY_ID));
//
//
//        //Неизвестно имя родительской категории
//        Category parent = new Category("", null);
//        parent.setId(parentCategoryId);
//
//        return new Category(categoryName,parent);
//    }
//}
