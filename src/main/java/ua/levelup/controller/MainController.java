package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.levelup.controller.support.ControllerUtils;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final String ROOT = "/";
    private static final String LOGIN = "/login";
    private static final String SUCCESS = "/success";
    private static final String VALIDATION_ERROR = "/validationerror";
    private static final String INDEX_PAGE = "index";
    private static final String LOGIN_PAGE = "login";
    private static final String SUCCESS_PAGE = "success";
    private static final String VALIDATION_ERROR_PAGE = "validationerror";

    @Autowired
    private ControllerUtils controllerUtils;

    @GetMapping(value = ROOT)
    public String indexPage(HttpServletRequest request) {
        controllerUtils.setDefaultAttributes(request.getSession(true));
        return INDEX_PAGE;
    }

    @GetMapping(value = LOGIN)
    public String loginPage(){
        return LOGIN_PAGE;
    }

    @GetMapping(value=SUCCESS)
    public String sucessPage(){
        return SUCCESS_PAGE;
    }

    @GetMapping(value=VALIDATION_ERROR)
    public String validationErrorPage(){
        return VALIDATION_ERROR_PAGE;
    }
}