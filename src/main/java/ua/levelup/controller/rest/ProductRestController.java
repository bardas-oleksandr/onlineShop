package ua.levelup.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.RestException;
import ua.levelup.model.Product;
import ua.levelup.service.ProductService;
import ua.levelup.web.dto.SearchParamsDto;
import ua.levelup.web.dto.create.ProductCreateDto;
import ua.levelup.web.dto.view.ProductViewDto;

import javax.validation.Valid;
import java.util.List;
import java.util.Properties;

/**
 *
 */
@RestController
@RequestMapping(value = "/rest/product"
        , consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
        , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
public class ProductRestController {

    private static final String ID = "/{id}";
    private static final String SEARCH = "/search";

    @Autowired
    private ProductService productService;

    @Autowired
    private Properties messagesProperties;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    ProductViewDto create(@Valid @RequestBody ProductCreateDto productCreateDto, BindingResult result) {
        controllerUtils.checkValidationViolations(result);
        try {
            return productService.create(productCreateDto);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"))) {
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
    ProductViewDto get(@PathVariable("id") int productId) {
        try {
            return productService.get(productId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("EMPTY_RESULTSET") + Product.class)) {
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
    ProductViewDto update(@Valid @RequestBody ProductCreateDto productCreateDto
            , BindingResult result, @PathVariable("id") int productId) {
        controllerUtils.checkValidationViolations(result);
        try {
            return productService.update(productCreateDto, productId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_PRODUCT_NONEXISTENT"))) {
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
    public void delete(@PathVariable("id") int productId) {
        try {
            productService.delete(productId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_PRODUCT"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_PRODUCT_NONEXISTENT"))) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND, message);
            } else {
                //http status 500
                throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, message);
            }
        }
    }

    @PostMapping(value = SEARCH)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<ProductViewDto> searchProducts(@RequestBody SearchParamsDto searchParamsDto) {
        try {
            List<ProductViewDto> list = productService.searchProducts(searchParamsDto);
            if (list.size() == 0) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND,
                        messagesProperties.getProperty("EMPTY_RESULTSET") + Product.class);
            }
            return list;
        } catch (ApplicationException e) {
            //http status 500
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
