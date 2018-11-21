package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.levelup.dao.UserDao;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final String ID = "/{id}";
    private static final String REDIRECT_USER = "redirect:/user/";
    private static final String USERS_PAGE = "users";
    private static final String USER_LIST_ATTRIBUTE = "userList";

    @Autowired
    private UserService userService;

    @GetMapping
    public String usersPage(ModelMap modelMap, HttpServletRequest request) {
        List<UserViewDto> userViewDtos = userService.getAllUsers();
        modelMap.addAttribute(USER_LIST_ATTRIBUTE, userViewDtos);
        return USERS_PAGE;
    }

    @PostMapping
    public String register(ModelMap modelMap) {

        int userId = 1;
        return REDIRECT_USER + userId;
    }

    @PostMapping(value = ID)
    public String modifyUser(ModelMap modelMap, @PathVariable("id") int userId
            , @ModelAttribute("userCreateDto") UserCreateDto userCreateDto) {
        userService.updateUser(userCreateDto, userId);
        return REDIRECT_USER;
    }
}