package ua.levelup.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.RestException;
import ua.levelup.model.User;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.create.UserRegisterCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Properties;

/**
 *
 */
@RestController
@RequestMapping(value = "/rest/user"
        , consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
        , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
public class UserRestController {

    private static final String ID = "/{id}";

    @Autowired
    private UserService userService;

    @Autowired
    private Properties messagesProperties;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    UserViewDto create(@Valid @RequestBody UserRegisterCreateDto userRegisterCreateDto,
                       BindingResult result) {
        controllerUtils.checkValidationViolations(result);
        try {
            return userService.create(userRegisterCreateDto);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("NOT_UNIQUE_USER"))) {
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
    UserViewDto get(@PathVariable("id") int userId) {
        try {
            return userService.getUserViewDtoById(userId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("EMPTY_RESULTSET") + User.class)) {
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
    UserViewDto update(@Valid @RequestBody UserCreateDto userCreateDto,
                       BindingResult result, @PathVariable("id") int userId) {
        controllerUtils.checkValidationViolations(result);
        try {
            return userService.update(userCreateDto, userId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("NOT_UNIQUE_USER"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_USER_NONEXISTENT"))) {
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
    public void delete(@PathVariable("id") int userId) {
        try {
            userService.delete(userId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties
                    .getProperty("FAILED_DELETE_USER_NONEXISTENT"))) {
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
    List<UserViewDto> getAll() {
        try {
            List<UserViewDto> list = userService.getAll();
            if (list.size() == 0) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND,
                        messagesProperties.getProperty("EMPTY_RESULTSET") + User.class);
            }
            return list;
        } catch (ApplicationException e) {
            //http status 500
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
