package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.SearchUtils;
import ua.levelup.service.ProductService;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.view.ProductViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final String ID = "/{id}";
    private static final String DELETE = "/delete";
    private static final String DEFAULT_FILTER = "/default";
    private static final String SUCCESS_PAGE = "success";
    private static final String INDEX_PAGE = "index";
    private static final String PRODUCT_LIST_ATTRIBUTE = "productList";
    private static final String SEARCH_PARAMS_ATTRIBUTE = "searchParams";

    @Autowired
    private ProductService productService;

    @Autowired
    private SearchUtils searchUtils;

    @GetMapping
    public String searchProducts(HttpServletRequest request
            , @ModelAttribute SearchParamsDto searchParamsDto) {

        HttpSession session = request.getSession(true);
        session.setAttribute(SEARCH_PARAMS_ATTRIBUTE, searchParamsDto);

        List<ProductViewDto> productViewDtos = productService.searchProducts(searchParamsDto);
        session.setAttribute(PRODUCT_LIST_ATTRIBUTE, productViewDtos);

        return INDEX_PAGE;
    }

    @GetMapping(value = DEFAULT_FILTER)
    public String defaultFilter(HttpServletRequest request) {

        HttpSession session = request.getSession(true);
        searchUtils.setDefaultSearchParams(session);

        SearchParamsDto searchParamsDto = (SearchParamsDto) session.getAttribute(SEARCH_PARAMS_ATTRIBUTE);
        List<ProductViewDto> productViewDtos = productService.searchProducts(searchParamsDto);
        session.setAttribute(PRODUCT_LIST_ATTRIBUTE, productViewDtos);

        return INDEX_PAGE;
    }

    @PostMapping
    public String createProduct(ModelMap modelMap) {

        return SUCCESS_PAGE;
    }

    @PostMapping(value = ID)
    public String modifyProduct(ModelMap modelMap, @PathVariable("id") int productId) {

        return INDEX_PAGE;
    }

    @PostMapping(value = DELETE + ID)
    public String deleteProduct(ModelMap modelMap, @PathVariable("id") int productId) {

        return INDEX_PAGE;
    }
}
