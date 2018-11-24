package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.controller.support.MessageResolver;
import ua.levelup.service.ManufacturerService;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

import javax.validation.Valid;

/**
 *
 */
@Controller
@RequestMapping(value = "/manufacturer")
public class ManufacturerController {

    private static final String ERROR_PAGE = "error";
    private static final String REDIRECT_ROOT = "redirect:/";
    private static final String REDIRECT_SUCCESS = "redirect:/success";
    private static final String MESSAGE_CODE_ATTRIBUTE = "message_code";

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ControllerUtils controllerUtils;

    @Autowired
    private MessageResolver messageResolver;

    @GetMapping
    public String redirectRoot() {
        return REDIRECT_ROOT;
    }

    @PostMapping
    public String createManufacturer(@Valid @ModelAttribute ManufacturerCreateDto manufacturerCreateDto
            , BindingResult result, ModelMap modelMap) {

        if (result.hasErrors()) {
            return controllerUtils.redirectValidationError(result, modelMap);
        }
        manufacturerService.create(manufacturerCreateDto);
        return REDIRECT_SUCCESS;
    }

    @ExceptionHandler({Exception.class})
    public ModelAndView handleException(Exception e) {
        String messageCode = messageResolver.resolveMessageForException(e);
        ModelAndView modelAndView = new ModelAndView(ERROR_PAGE);
        modelAndView.addObject(MESSAGE_CODE_ATTRIBUTE, messageCode);
        return modelAndView;
    }
}