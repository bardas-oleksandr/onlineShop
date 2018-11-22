package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.service.ProductService;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.view.ProductViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(value = "/search")
public class SearchController {

    private static final String DEFAULT_FILTER = "/default";
    private static final String INDEX_PAGE = "index";
    private static final String REDIRECT_SEARCH = "redirect:/search/";
    private static final String PRODUCT_LIST_ATTRIBUTE = "productList";
    private static final String SEARCH_PARAMS_ATTRIBUTE = "searchParams";

    @Autowired
    private ProductService productService;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    public String setSearchParams(@Valid @ModelAttribute SearchParamsDto searchParamsDto
            , BindingResult result, ModelMap modelMap, HttpServletRequest request) {

        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        HttpSession session = request.getSession(true);
        session.setAttribute(SEARCH_PARAMS_ATTRIBUTE, searchParamsDto);
        return REDIRECT_SEARCH;
    }

    @PostMapping(value = DEFAULT_FILTER)
    public String defaultFilter(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        controllerUtils.setDefaultSearchParams(session);
        return REDIRECT_SEARCH;
    }

    @GetMapping
    public String searchProducts(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        SearchParamsDto searchParamsDto = (SearchParamsDto) session
                .getAttribute(SEARCH_PARAMS_ATTRIBUTE);
        if (searchParamsDto != null) {
            List<ProductViewDto> productViewDtos = productService.searchProducts(searchParamsDto);
            session.setAttribute(PRODUCT_LIST_ATTRIBUTE, productViewDtos);
        }
        return INDEX_PAGE;
    }
}
