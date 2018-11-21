package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.service.ManufacturerService;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

import javax.validation.Valid;

@Controller
@RequestMapping(value = "/manufacturer")
public class ManufacturerController {

    private static final String ERROR_PAGE = "error";
    private static final String REDIRECT_ROOT = "redirect:/";
    private static final String REDIRECT_SUCCESS = "redirect:/success";

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ControllerUtils controllerUtils;

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
        manufacturerService.createManufacturer(manufacturerCreateDto);
        return REDIRECT_SUCCESS;
    }

    @ExceptionHandler({Exception.class})
    public String handleException(ModelMap modelMap, Exception e) {
        modelMap.addAttribute("errorMessage", e);
        return ERROR_PAGE;
    }
}