package ua.levelup.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.levelup.service.ManufacturerService;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

@Controller
@RequestMapping(value = "/manufacturer")
public class ManufacturerController {

    private static final String SUCCESS_PAGE = "success";

    @Autowired
    private ManufacturerService manufacturerService;

    @PostMapping
    public String createManufacturer(@ModelAttribute ManufacturerCreateDto manufacturerCreateDto){
        manufacturerService.createManufacturer(manufacturerCreateDto);
        return SUCCESS_PAGE;
    }
}