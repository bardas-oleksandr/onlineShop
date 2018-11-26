package ua.levelup.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.RestException;
import ua.levelup.model.Manufacturer;
import ua.levelup.service.ManufacturerService;
import ua.levelup.web.dto.create.ManufacturerCreateDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Properties;

/**
 *
 */
@RestController
@RequestMapping(value = "/rest/manufacturer"
        , consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
        , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ManufacturerRestController {

    private static final String ID = "/{id}";

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private Properties messagesProperties;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ManufacturerViewDto create(@Valid @RequestBody ManufacturerCreateDto manufacturerCreateDto,
                               BindingResult result) {
        controllerUtils.checkValidationViolations(result);
        try {
            return manufacturerService.create(manufacturerCreateDto);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("DATA_INTEGRITY_VIOLATION_FOR_MANUFACTURER"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_MANUFACTURER"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else {
                //http status 500
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
            }
        }
    }

    @GetMapping(value = ID)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ManufacturerViewDto get(@PathVariable("id") int manufacturerId) {
        try {
            return manufacturerService.get(manufacturerId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("EMPTY_RESULTSET") + Manufacturer.class)) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND, message);
            } else {
                //http status 500
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
            }
        }
    }

    @PutMapping(value = ID)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    ManufacturerViewDto update(@Valid @RequestBody ManufacturerCreateDto manufacturerCreateDto,
                               BindingResult result, @PathVariable("id") int manufacturerId) {
        controllerUtils.checkValidationViolations(result);
        try {
            return manufacturerService.update(manufacturerCreateDto, manufacturerId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties
                    .getProperty("DATA_INTEGRITY_VIOLATION_FOR_MANUFACTURER"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_MANUFACTURER"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_MANUFACTURER_NONEXISTENT"))) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND, message);
            } else {
                //http status 500
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
            }
        }
    }

    @DeleteMapping(value = ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") int manufacturerId) {
        try {
            manufacturerService.delete(manufacturerId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties
                    .getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_MANUFACTURER"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_MANUFACTURER_NONEXISTENT"))) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND, message);
            } else {
                //http status 500
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
            }
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ManufacturerViewDto> getAll() {
        try {
            List<ManufacturerViewDto> list = manufacturerService.getAll();
            if (list.size() == 0) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND,
                        messagesProperties.getProperty("EMPTY_RESULTSET") + Manufacturer.class);
            }
            return list;
        } catch (ApplicationException e) {
            //http status 500
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}