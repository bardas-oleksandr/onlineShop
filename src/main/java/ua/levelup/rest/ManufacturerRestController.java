package ua.levelup.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.service.ManufacturerService;
import ua.levelup.web.dto.create.ManufacturerCreateDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/rest/category"
        , consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
        , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
        , headers = {"content-type=" + MediaType.APPLICATION_JSON_UTF8_VALUE
        , "content-type=" + MediaType.APPLICATION_XML_VALUE
        , "accept=" + MediaType.APPLICATION_JSON_UTF8_VALUE
        , "accept=" + MediaType.APPLICATION_XML_VALUE})
public class ManufacturerRestController {

    private static final String ID = "/{id}";

    @Autowired
    private ManufacturerService manufacturerService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ManufacturerViewDto create(@Valid @RequestBody ManufacturerCreateDto manufacturerCreateDto,
                               BindingResult result) {
        if (result.hasErrors()) {

        }
        return manufacturerService.create(manufacturerCreateDto);
    }

    @GetMapping(value = ID)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ManufacturerViewDto get(int manufacturerId) {
        return manufacturerService.get(manufacturerId);
    }

    @PutMapping(value = ID)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ManufacturerViewDto update(@Valid @RequestBody ManufacturerCreateDto manufacturerCreateDto,
                               BindingResult result, @PathVariable("id") int manufacturerId) {
        if (result.hasErrors()) {

        }
        return manufacturerService.update(manufacturerCreateDto, manufacturerId);
    }

    @DeleteMapping(value = ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int manufacturerId) {
        manufacturerService.delete(manufacturerId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ManufacturerViewDto> getAll() {
        return manufacturerService.getAll();
    }


}
