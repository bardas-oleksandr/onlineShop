package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.controller.support.MessageResolver;
import ua.levelup.model.User;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.UserRegisterCreateDto;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.Validator;
import java.util.List;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    private static final String ID = "/{id}";
    private static final String REDIRECT_SECURITY_CHECK = "redirect:/j_spring_security_check/";
    private static final String REDIRECT_USER = "redirect:/user/";
    private static final String USERS_PAGE = "users";
    private static final String ERROR_PAGE = "error";
    private static final String ID_ATTRIBUTE = "id";
    private static final String USER_LIST_ATTRIBUTE = "userList";
    private static final String EMAIL_ATTRIBUTE = "email";
    private static final String PASSWORD_ATTRIBUTE = "password";
    private static final String MESSAGE_CODE_ATTRIBUTE = "message_code";

    @Autowired
    private UserService userService;

    @Autowired
    private Validator validator;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private MessageResolver messageResolver;

    @PostMapping
    public String register(@Valid @ModelAttribute UserRegisterCreateDto userRegisterCreateDto
            , BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        String password = userRegisterCreateDto.getPassword();
        User user = userService.registerUser(userRegisterCreateDto);
        request.setAttribute(EMAIL_ATTRIBUTE, user.getEmail());
        request.setAttribute(PASSWORD_ATTRIBUTE, password);
        return REDIRECT_SECURITY_CHECK;
    }

    @PostMapping(value = ID)
    public String updateUser(ModelMap modelMap, @PathVariable(ID_ATTRIBUTE) int userId
            , @ModelAttribute UserCreateDto userCreateDto, BindingResult result) {

        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        userService.updateUser(userCreateDto, userId);
        return REDIRECT_USER;
    }

    @GetMapping
    public String usersPage(ModelMap modelMap, HttpServletRequest request) {
        List<UserViewDto> userViewDtos = userService.getAllUsers();
        modelMap.addAttribute(USER_LIST_ATTRIBUTE, userViewDtos);
        return USERS_PAGE;
    }

    @GetMapping(value = ID)
    public String redirectOnUser(){
        return REDIRECT_USER;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception e) {
        String messageCode = messageResolver.resolveMessageForException(e);
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(MESSAGE_CODE_ATTRIBUTE, messageCode);
        return modelAndView;
    }
}