package ua.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final String ID = "/{id}";

    private static final String USERS_PAGE = "users";
    private static final String PROFILE_PAGE = "profile";
    private static final String REDIRECT_USER = "redirect:/user/";

    @GetMapping(value = ID)
    public String profilePage(ModelMap modelMap, @PathVariable("id") int userId) {

        return PROFILE_PAGE;
    }

    @GetMapping
    public String usersPage(ModelMap modelMap) {

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
