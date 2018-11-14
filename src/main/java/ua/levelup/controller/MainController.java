package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.levelup.controller.support.FilterUtils;

@Controller
public class MainController {

    private static final String ROOT = "/";
    private static final String INDEX_PAGE = "index";
    private static final String PROFILE = "/profile";
    private static final String SIGN_IN = "/signIn";
    private static final String SIGN_OUT = "/signOut";
    private static final String REDIRECT_INDEX_PAGE = "redirect:/index";
    private static final String REDIRECT_USER = "redirect:/user/";

    @Autowired
    private FilterUtils filterUtils;

    @GetMapping(value = ROOT)
    public String indexPage(ModelMap modelMap) {
        filterUtils.setDefaultAttributes(modelMap);
        return INDEX_PAGE;
    }

    @PostMapping(value = SIGN_IN)
    public String signIn(ModelMap modelMap) {

        int userId = 1;
        return REDIRECT_USER + userId;
    }

    @PostMapping(value = SIGN_OUT)
    public String signOut(ModelMap modelMap) {

        return REDIRECT_INDEX_PAGE;
    }
}