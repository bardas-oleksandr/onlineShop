package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.levelup.controller.support.SearchUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final String ROOT = "/";
    private static final String LOGIN = "/login";
    private static final String ERROR = "/error";
    private static final String INDEX_PAGE = "index";
    private static final String LOGIN_PAGE = "login";
    private static final String ERROR_PAGE = "error";

    @Autowired
    private SearchUtils filterUtils;

    @GetMapping(value = ROOT)
    public String indexPage(HttpServletRequest request) {
        filterUtils.setDefaultAttributes(request.getSession(true));
        return INDEX_PAGE;
    }

    @GetMapping(value = LOGIN)
    public String loginPage(){
        return LOGIN_PAGE;
    }

    @GetMapping(value = ERROR)
    public String errorPage() {

        return ERROR_PAGE;
    }
}