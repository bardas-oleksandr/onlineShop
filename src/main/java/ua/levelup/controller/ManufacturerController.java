package ua.levelup.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/manufacturer")
public class ManufacturerController {

    private static final String ID = "/{id}";
    private static final String DELETE = "/delete";
    private static final String SUCCESS_PAGE = "manufacturerCreated";
    private static final String MANUFACTURER_PAGE = "manufacturer";

    @PostMapping
    public String createManufacturer(ModelMap modelMap){

        return SUCCESS_PAGE;
    }

    @GetMapping
    public String getManufacturers(ModelMap modelMap){

        return MANUFACTURER_PAGE;
    }

    @PostMapping(value = ID)
    public String modifyManufacturer(ModelMap modelMap, @PathVariable("id") int manufacturerId){

        return MANUFACTURER_PAGE;
    }

    @PostMapping(value = DELETE + ID)
    public String deleteManufacturer(ModelMap modelMap, @PathVariable("id") int manufacturerId){

        return MANUFACTURER_PAGE;
    }
}
