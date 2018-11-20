package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import ua.levelup.controller.support.SearchUtils;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.view.UserViewDto;

import javax.servlet.http.HttpServletRequest;

@Controller
public class MainController {

    private static final String ROOT = "/";
    private static final String LOGIN = "/login";
    private static final String PROFILE = "/profile";
    private static final String ERROR = "/error";
    private static final String INDEX_PAGE = "index";
    private static final String LOGIN_PAGE = "login";
    private static final String PROFILE_PAGE = "profile";
    private static final String ERROR_PAGE = "error";
    private static final String SPRING_SECURITY_CONTEXT = "SPRING_SECURITY_CONTEXT";
    private static final String USER_ATTRIBUTE = "user";

    @Autowired
    private SearchUtils filterUtils;

    @Autowired
    private UserService userService;

    @GetMapping(value = ROOT)
    public String indexPage(HttpServletRequest request) {
        filterUtils.setDefaultAttributes(request.getSession(true));
        return INDEX_PAGE;
    }

    @GetMapping(value = LOGIN)
    public String loginPage(){
        return LOGIN_PAGE;
    }

    @GetMapping(value = PROFILE)
    public String profilePage(ModelMap modelMap, HttpServletRequest request) {
        SecurityContextImpl securityContext = (SecurityContextImpl) request
                .getSession(true).getAttribute(SPRING_SECURITY_CONTEXT);
        User user = (User) securityContext.getAuthentication().getPrincipal();
        String email = user.getUsername();

        UserViewDto viewDto = userService.getUserViewDto(email);
        modelMap.addAttribute(USER_ATTRIBUTE, viewDto);

        return PROFILE_PAGE;
    }

    @GetMapping(value = ERROR)
    public String errorPage() {

        return ERROR_PAGE;
    }
}