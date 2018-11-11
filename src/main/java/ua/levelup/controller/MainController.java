package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.levelup.controller.support.FilterUtils;

@Controller
public class MainController {

    private static final String INDEX_PAGE = "index";
    private static final String ROOT = "/";

    @Autowired
    private FilterUtils filterUtils;

    @RequestMapping(value = ROOT, method = RequestMethod.GET)
    public String indexPage(ModelMap modelMap) {
        filterUtils.setDefaultAttributes(modelMap);
        return INDEX_PAGE;
    }
}
