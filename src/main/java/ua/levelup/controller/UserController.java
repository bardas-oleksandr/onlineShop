package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.levelup.dao.UserDao;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final String ID = "/{id}";

    private static final String USERS_PAGE = "users";
    private static final String ERROR_PAGE = "error";
    private static final String REDIRECT_USER = "redirect:/user/";

    @Autowired
    @Qualifier("conversionService")
    private ConversionService conversionService;

    @Autowired
    private UserDao userDao;

    @GetMapping
    public String usersPage(ModelMap modelMap, HttpServletRequest request) {

        return USERS_PAGE;
    }

    @PostMapping
    public String register(ModelMap modelMap) {

        int userId = 1;
        return REDIRECT_USER + userId;
    }

    @PostMapping(value = ID)
    public String modifyUser(ModelMap modelMap, @PathVariable("id") int userId) {

        return USERS_PAGE;
    }
}