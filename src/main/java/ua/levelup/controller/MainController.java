package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ua.levelup.controller.support.FilterUtils;

@Controller
public class MainController {

    private static final String ROOT = "/";
    private static final String INDEX_PAGE = "index";
    private static final String ERROR = "/error";
    private static final String ERROR_PAGE = "error";

    @Autowired
    private FilterUtils filterUtils;

    @GetMapping(value = ROOT)
    public String indexPage(ModelMap modelMap) {
        filterUtils.setDefaultAttributes(modelMap);
        return INDEX_PAGE;
    }

    @GetMapping(value = ERROR)
    public String errorPage() {

        return ERROR_PAGE;
    }
}