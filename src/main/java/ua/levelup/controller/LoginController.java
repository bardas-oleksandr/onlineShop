package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.levelup.dao.UserDao;
import ua.levelup.exception.ValidationException;
import ua.levelup.model.Credentials;
import ua.levelup.model.User;
import ua.levelup.service.SecurityService;
import ua.levelup.web.dto.create.CredentialsCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Controller
public class LoginController {

    private static final String SIGN_IN = "/signIn";
    private static final String SIGN_OUT = "/signOut";
    private static final String REDIRECT_INDEX_PAGE = "redirect:/";
    private static final String REDIRECT_USER = "redirect:/user/";
    private static final String ERROR_PAGE = "error";
    private static final String USER_ATTRIBUTE = "user";
    private static final String CREDENTIALS_ATTRIBUTE = "credentials";

    @Autowired
    @Qualifier("conversionService")
    private ConversionService conversionService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @PostMapping(value = SIGN_IN)
    public String signIn(@ModelAttribute(CREDENTIALS_ATTRIBUTE) @Valid CredentialsCreateDto createDto
            , BindingResult result, HttpServletRequest request) {

        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().get(0).getCode());
        }

        Credentials credentials = conversionService.convert(createDto, Credentials.class);
        User user = userDao.getByEmail(credentials.getEmail());

        if (securityService.isCorrectPassword(user, credentials.getPassword())){
            UserViewDto userViewDto = conversionService.convert(user, UserViewDto.class);
            request.getSession(true).setAttribute(USER_ATTRIBUTE, userViewDto);
            return REDIRECT_USER + user.getId();
        }else{
            throw new ValidationException("wrong_password");
        }
    }

    @PostMapping(value = SIGN_OUT)
    public String signOut(HttpServletRequest request) {
        request.getSession(true).removeAttribute(USER_ATTRIBUTE);
        return REDIRECT_INDEX_PAGE;
    }
}
