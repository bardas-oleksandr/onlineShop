package ua.levelup.controller.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import ua.levelup.service.CategoryService;
import ua.levelup.service.ManufacturerService;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.view.CategoryViewDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Component("controllerUtils")
public class ControllerUtils {

    private static final int DEFAULT_CATEGORY_ID = 0;
    private static final int DEFAULT_SUBCATEGORY_ID = 0;
    private static final int DEFAULT_MANUFACTURER_ID = 0;
    private static final boolean DEFAULT_PRODUCT_AVAILABILITY = true;
    private static final float DEFAULT_MIN_PRICE = 0.0f;
    private static final float DEFAULT_MAX_PRICE = 1000.0f;
    private static final int DEFAULT_ORDER_METHOD_INDEX = 0;

    private static final String CATEGORY_LIST_ATTRIBUTE = "categoryList";
    private static final String SUBCATEGORY_LIST_ATTRIBUTE = "subcategoryList";
    private static final String MANUFACTURER_LIST_ATTRIBUTE = "manufacturerList";
    private static final String SEARCH_PARAMS_ATTRIBUTE = "searchParams";
    private static final String MESSAGE_CODES_ATTRIBUTE = "messageCodes";

    private static final String VALIDATION_ERROR_PAGE = "validationerror";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ManufacturerService manufacturerService;

    public void setDefaultAttributes(HttpSession session) {

        List<CategoryViewDto> categoryList = categoryService.getCategoriesByLevel(0);
        List<CategoryViewDto> subcategoryList = categoryService.getCategoriesByLevel(1);
        List<ManufacturerViewDto> manufacturerList = manufacturerService.getAllManufacturers();

        session.setAttribute(CATEGORY_LIST_ATTRIBUTE, categoryList);
        session.setAttribute(SUBCATEGORY_LIST_ATTRIBUTE, subcategoryList);
        session.setAttribute(MANUFACTURER_LIST_ATTRIBUTE, manufacturerList);

        SearchParamsDto searchParamsDto = (SearchParamsDto) session.getAttribute(SEARCH_PARAMS_ATTRIBUTE);
        if (searchParamsDto == null) {
            setDefaultSearchParams(session);
        }
    }

    public void setDefaultSearchParams(HttpSession session) {
        SearchParamsDto searchParamsDto = new SearchParamsDto(DEFAULT_CATEGORY_ID
                , DEFAULT_SUBCATEGORY_ID, DEFAULT_MANUFACTURER_ID, DEFAULT_PRODUCT_AVAILABILITY
                , DEFAULT_MIN_PRICE, DEFAULT_MAX_PRICE, DEFAULT_ORDER_METHOD_INDEX);
        session.setAttribute(SEARCH_PARAMS_ATTRIBUTE, searchParamsDto);
    }

    public String redirectValidationError(BindingResult result, ModelMap modelMap) {
        List<String> messageCodes = new ArrayList<>();
        result.getAllErrors().stream().forEach((errorMessage) -> messageCodes
                .add(errorMessage.getDefaultMessage()));
        modelMap.addAttribute(MESSAGE_CODES_ATTRIBUTE, messageCodes);
        return VALIDATION_ERROR_PAGE;
    }
}
