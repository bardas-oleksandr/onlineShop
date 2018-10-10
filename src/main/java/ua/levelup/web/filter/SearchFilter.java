package ua.levelup.web.filter;

import ua.levelup.web.dto.create.SearchParamsCreateDto;
import ua.levelup.service.support.SearchUtils;
import ua.levelup.web.servlet.support.ServletUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class SearchFilter implements Filter {

    private static final Logger logger = LogManager.getLogger();

    private static final String SEARCH_PARAMS = "searchParams";
    private static final String ERROR_JSP = "error.jsp";
    private static final String EXCEPTION = "exception";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            SearchUtils.initSearchingModel(request);

            HttpSession session = request.getSession(true);
            SearchParamsCreateDto searchParams = (SearchParamsCreateDto) session.getAttribute(SEARCH_PARAMS);
            if(searchParams == null){
                searchParams = ServletUtils.getDefaultSearchParams();
                session.setAttribute(SEARCH_PARAMS, searchParams);
            }
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            servletRequest.setAttribute(EXCEPTION, e);
            logger.error("Exception: " + e.getClass() +
                    "\tMessage: "  + e.getMessage() + "\tCause:" + e.getCause());
            servletRequest.getRequestDispatcher(ERROR_JSP).forward(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
