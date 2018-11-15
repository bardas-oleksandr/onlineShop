package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ua.levelup.controller.support.FilterUtils;
import ua.levelup.dao.UserDao;
import ua.levelup.exception.ValidationException;
import ua.levelup.model.Credentials;
import ua.levelup.model.User;
import ua.levelup.service.SecurityService;
import ua.levelup.web.dto.create.CredentialsCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.validation.Valid;

@Controller
public class MainController {

    private static final String ROOT = "/";
    private static final String PROFILE = "/profile";
    private static final String SIGN_IN = "/signIn";
    private static final String SIGN_OUT = "/signOut";
    private static final String REDIRECT_INDEX_PAGE = "redirect:/index";
    private static final String REDIRECT_USER = "redirect:/user/";
    private static final String INDEX_PAGE = "index";
    private static final String ERROR_PAGE = "error";

    @Autowired
    private FilterUtils filterUtils;

    @Autowired
    @Qualifier("conversionService")
    private ConversionService conversionService;

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @GetMapping(value = ROOT)
    public String indexPage(ModelMap modelMap) {
        filterUtils.setDefaultAttributes(modelMap);
        return INDEX_PAGE;
    }

    @PostMapping(value = SIGN_IN)
    public String signIn(@ModelAttribute("credentials") @Valid CredentialsCreateDto createDto
            , BindingResult result, ModelMap modelMap) {

        if(result.hasErrors()){
            throw new ValidationException(result.getAllErrors().get(0).getCode());
        }

        Credentials credentials = conversionService.convert(createDto, Credentials.class);
        User user = userDao.getByEmail(credentials.getEmail());

        if (securityService.isCorrectPassword(user, credentials.getPassword())){
            UserViewDto userViewDto = conversionService.convert(user, UserViewDto.class);
            modelMap.addAttribute("user", userViewDto);
            return REDIRECT_USER + user.getId();
        }else{
            throw new ValidationException("wrong_password");
        }
    }

    @PostMapping(value = SIGN_OUT)
    public String signOut(ModelMap modelMap) {

        return REDIRECT_INDEX_PAGE;
    }

    private CredentialsCreateDto extractCredentials(ModelMap modelMap) {
        CredentialsCreateDto createDto = new CredentialsCreateDto();
        //createDto.setEmail(modelMap.);
        //createDto.setPassword();
        return createDto;
    }
}