package ua.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    private static final String LOGIN = "/login";
    private static final String LOGIN_PAGE = "login";

    @GetMapping(value = LOGIN)
    public String loginPage(){
        return LOGIN_PAGE;
    }
}
