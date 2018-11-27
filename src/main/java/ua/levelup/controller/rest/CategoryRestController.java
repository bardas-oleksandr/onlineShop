package ua.levelup.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.RestException;
import ua.levelup.model.Category;
import ua.levelup.service.CategoryService;
import ua.levelup.web.dto.create.CategoryCreateDto;
import ua.levelup.web.dto.view.CategoryViewDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Properties;

/**
 *
 */
@RestController
@RequestMapping(value = "/rest/category"
        , consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        , produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class CategoryRestController {

    private static final String ID = "/{id}";
    private static final String LEVEL = "/level";
    private static final String PARENT = "/parent";

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private Properties messagesProperties;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    CategoryViewDto create(@Valid @RequestBody CategoryCreateDto categoryCreateDto, BindingResult result) {
        controllerUtils.checkValidationViolations(result);
        try {
            return categoryService.create(categoryCreateDto);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_CATEGORY"))) {
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
    CategoryViewDto get(@PathVariable("id") int categoryId) {
        try {
            return categoryService.get(categoryId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("EMPTY_RESULTSET") + Category.class)) {
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
    CategoryViewDto update(@Valid @RequestBody CategoryCreateDto categoryCreateDto, BindingResult result
            , @PathVariable("id") int categoryId) {
        controllerUtils.checkValidationViolations(result);
        try {
            return categoryService.update(categoryCreateDto, categoryId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_CATEGORY"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_CATEGORY_NONEXISTENT"))) {
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
    public void delete(@PathVariable("id") int categoryId) {
        try {
            categoryService.delete(categoryId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_CATEGORY"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_CATEGORY_NONEXISTENT"))) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND, message);
            } else {
                //http status 500
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
            }
        }
    }

    @GetMapping(value = LEVEL + ID)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<CategoryViewDto> getAllByLevel(@PathVariable("id") int categoryLevel) {
        try {
            List<CategoryViewDto> list = categoryService.getCategoriesByLevel(categoryLevel);
            if (list.size() == 0) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND,
                        messagesProperties.getProperty("EMPTY_RESULTSET") + Category.class);
            }
            return list;
        } catch (ApplicationException e) {
            //http status 500
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(value = PARENT + ID)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<CategoryViewDto> getAllByParentId(@PathVariable("id") int parentId) {
        try {
            List<CategoryViewDto> list = categoryService.getCategoriesByParentId(parentId);
            if (list.size() == 0) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND,
                        messagesProperties.getProperty("EMPTY_RESULTSET") + Category.class);
            }
            return list;
        } catch (ApplicationException e) {
            //http status 500
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
