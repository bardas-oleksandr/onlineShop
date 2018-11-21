package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.SearchUtils;
import ua.levelup.service.ProductService;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.view.ProductViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductController {

    private static final String ID = "/{id}";
    private static final String DELETE = "/delete";
    private static final String SEARCH = "/search";
    private static final String DEFAULT_FILTER = "/default";
    private static final String REDIRECT_SEARCH = "redirect:/product/search/";
    private static final String SUCCESS_PAGE = "success";
    private static final String INDEX_PAGE = "index";
    private static final String ID_ATTRIBUTE = "id";
    private static final String PRODUCT_ATTRIBUTE = "product";
    private static final String PRODUCT_LIST_ATTRIBUTE = "productList";
    private static final String SEARCH_PARAMS_ATTRIBUTE = "searchParams";

    @Autowired
    private ProductService productService;

    @Autowired
    private SearchUtils searchUtils;

    @GetMapping
    public String setSearchParams(HttpServletRequest request
            , @ModelAttribute(SEARCH_PARAMS_ATTRIBUTE) SearchParamsDto searchParamsDto) {
        HttpSession session = request.getSession(true);
        session.setAttribute(SEARCH_PARAMS_ATTRIBUTE, searchParamsDto);
        return REDIRECT_SEARCH;
    }

    @GetMapping(value = DEFAULT_FILTER)
    public String defaultFilter(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        searchUtils.setDefaultSearchParams(session);
        return REDIRECT_SEARCH;
    }

    @PostMapping(value = ID)
    public String updateProduct(@PathVariable(ID_ATTRIBUTE) int productId
            , @ModelAttribute(PRODUCT_ATTRIBUTE) ProductCreateDto productCreateDto) {
        productService.updateProduct(productCreateDto, productId);
        return REDIRECT_SEARCH;
    }

    @PostMapping(value = DELETE + ID)
    public String deleteProduct(@PathVariable(ID_ATTRIBUTE) int productId) {
        productService.deleteProduct(productId);
        return REDIRECT_SEARCH;
    }

    @GetMapping(value = SEARCH)
    public String searchProducts(HttpServletRequest request){
        HttpSession session = request.getSession(true);
        SearchParamsDto searchParamsDto = (SearchParamsDto) session
                .getAttribute(SEARCH_PARAMS_ATTRIBUTE);
        if(searchParamsDto != null){
            List<ProductViewDto> productViewDtos = productService.searchProducts(searchParamsDto);
            session.setAttribute(PRODUCT_LIST_ATTRIBUTE, productViewDtos);
        }
        return INDEX_PAGE;
    }

    @PostMapping
    public String createProduct(@ModelAttribute(PRODUCT_ATTRIBUTE) ProductCreateDto productCreateDto) {
        productService.createProduct(productCreateDto);
        return SUCCESS_PAGE;
    }
}