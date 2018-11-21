package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ua.levelup.model.User;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final String ID = "/{id}";
    private static final String REDIRECT_SECURITY_CHECK = "redirect:/j_spring_security_check/";
    private static final String REDIRECT_USER = "redirect:/user/";
    private static final String USERS_PAGE = "users";
    private static final String ID_ATTRIBUTE = "id";
    private static final String USER_LIST_ATTRIBUTE = "userList";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String PASSWORD_ATTRIBUTE = "password";

    @Autowired
    private UserService userService;

    @GetMapping
    public String usersPage(ModelMap modelMap, HttpServletRequest request) {
        List<UserViewDto> userViewDtos = userService.getAllUsers();
        modelMap.addAttribute(USER_LIST_ATTRIBUTE, userViewDtos);
        return USERS_PAGE;
    }

    @PostMapping
    public String register(@ModelAttribute UserCreateDto userCreateDto,
                           HttpServletRequest request) {
        userCreateDto.setUserStateIndex(User.UserState.ACTIVE.ordinal());
        String password = userCreateDto.getPassword();
        User user = userService.registerUser(userCreateDto);
        request.setAttribute(EMAIL_ATTRIBUTE, user.getEmail());
        request.setAttribute(PASSWORD_ATTRIBUTE, password);
        return REDIRECT_SECURITY_CHECK;
    }

    @PostMapping(value = ID)
    public String updateUser(ModelMap modelMap, @PathVariable(ID_ATTRIBUTE) int userId
            , @ModelAttribute UserCreateDto userCreateDto) {
        userService.updateUser(userCreateDto, userId);
        return REDIRECT_USER;
    }
}