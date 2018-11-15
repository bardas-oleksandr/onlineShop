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
import ua.levelup.model.User;
import ua.levelup.service.SecurityService;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final String ID = "/{id}";

    private static final String USERS_PAGE = "users";
    private static final String PROFILE_PAGE = "profile";
    private static final String ERROR_PAGE = "error";
    private static final String REDIRECT_USER = "redirect:/user/";

    @Autowired
    @Qualifier("conversionService")
    private ConversionService conversionService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @GetMapping(value = ID)
    public String profilePage(HttpServletRequest request, @PathVariable("id") int userId) {
        UserViewDto user = (UserViewDto) request.getSession(true).getAttribute("user");
        if(securityService.isAccessAllowed(user, userId)){
            return PROFILE_PAGE;
        }else{
            return ERROR_PAGE;
        }
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
