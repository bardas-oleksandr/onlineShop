package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.controller.support.MessageResolver;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.UserRegisterCreateDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 *
 */
@Controller
public class MainController {

    private static final String ROOT = "/";
    private static final String LOGIN = "/login";
    private static final String REGISTER = "/register";
    private static final String SUCCESS = "/success";
    private static final String LOGIN_FAILED = "/loginfailed";
    private static final String VALIDATION_ERROR = "/validationerror";
    private static final String INDEX_PAGE = "index";
    private static final String LOGIN_PAGE = "login";
    private static final String SUCCESS_PAGE = "success";
    private static final String ERROR_PAGE = "error";
    private static final String VALIDATION_ERROR_PAGE = "validationerror";
    private static final String REDIRECT_LOGIN = "redirect:/login/";
    private static final String MESSAGE_CODE_ATTRIBUTE = "message_code";
    private static final String LOGIN_FAILED_ATTRIBUTE = "login_failed";

    @Autowired
    private UserService userService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private MessageResolver messageResolver;

    @GetMapping(value = ROOT)
    public String indexPage(HttpServletRequest request) {
        controllerUtils.setDefaultAttributes(request.getSession(true));
        return INDEX_PAGE;
    }

    @GetMapping(value = LOGIN)
    public String loginPage() {
        return LOGIN_PAGE;
    }

    @GetMapping(value = LOGIN_FAILED)
    public ModelAndView loginFailed() {
        ModelAndView modelAndView = new ModelAndView(LOGIN_PAGE);
        modelAndView.addObject(LOGIN_FAILED_ATTRIBUTE,true);
        return modelAndView;
    }

    @PostMapping(value = REGISTER)
    public String register(@Valid @ModelAttribute UserRegisterCreateDto userRegisterCreateDto
            , BindingResult result, HttpServletRequest request, ModelMap modelMap) {
        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        userService.create(userRegisterCreateDto);
        return REDIRECT_LOGIN;
    }

    @GetMapping(value = SUCCESS)
    public String sucessPage() {
        return SUCCESS_PAGE;
    }

    @GetMapping(value = VALIDATION_ERROR)
    public String validationErrorPage() {
        return VALIDATION_ERROR_PAGE;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception e) {
        String messageCode = messageResolver.resolveMessageForException(e);
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(MESSAGE_CODE_ATTRIBUTE, messageCode);
        return modelAndView;
    }
}