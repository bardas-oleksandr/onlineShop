package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.levelup.exception.ValidationException;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.CredentialsCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    private static final String LOGIN = "/login";
    private static final String LOGOUT = "/logout";
    private static final String REDIRECT_INDEX_PAGE = "redirect:/";
    private static final String REDIRECT_USER = "redirect:/user/";
    private static final String ERROR_PAGE = "error";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CREDENTIALS_ATTRIBUTE = "credentials";

    @Autowired
    @Qualifier("conversionService")
    private ConversionService conversionService;

    @Autowired
    private UserService userService;

    @PostMapping(value = LOGIN)
    public String login(@ModelAttribute(CREDENTIALS_ATTRIBUTE) @Valid CredentialsCreateDto credentials
            , BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            throw new ValidationException(result.getAllErrors().get(0).getCode());
        }

        UserViewDto userViewDto = userService.login(credentials);
        request.getSession(true).setAttribute(USER_ATTRIBUTE, userViewDto);
        return REDIRECT_USER + userViewDto.getId();
    }

    @PostMapping(value = LOGOUT)
    public String logout(HttpServletRequest request) {
        request.getSession(true).removeAttribute(USER_ATTRIBUTE);
        return REDIRECT_INDEX_PAGE;
    }
}
