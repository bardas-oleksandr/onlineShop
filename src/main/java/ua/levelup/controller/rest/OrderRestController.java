package ua.levelup.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.levelup.controller.support.ControllerUtils;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.RestException;
import ua.levelup.model.Order;
import ua.levelup.service.OrderService;
import ua.levelup.web.dto.create.OrderCreateDto;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.OrderViewDto;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Properties;

/**
 *
 */
@RestController
@RequestMapping(value = "/rest/order"
        , consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE}
        , produces = {MediaType.APPLICATION_JSON_UTF8_VALUE, MediaType.APPLICATION_XML_VALUE})
public class OrderRestController {

    private static final String ID = "/{id}";
    private static final String USER = "/user";
    private static final String CART_ATTRIBUTE = "cart";

    @Autowired
    private OrderService orderService;

    @Autowired
    private Properties messagesProperties;

    @Autowired
    private ControllerUtils controllerUtils;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody
    OrderViewDto create(@Valid @RequestBody OrderCreateDto orderCreateDto, BindingResult result
            , HttpServletRequest request) {
        controllerUtils.checkValidationViolations(result);

        HttpSession session = request.getSession(true);
        CartViewDto cart = (CartViewDto) session.getAttribute(CART_ATTRIBUTE);
        if(cart == null || cart.getProductInCartViewDtoList().size() == 0){
            throw new RestException(HttpStatus.UNPROCESSABLE_ENTITY
                    , messagesProperties.getProperty("EMPTY_ORDER"));
        }

        try {
            return orderService.create(orderCreateDto, cart);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_ORDER"))) {
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
    OrderViewDto get(@PathVariable("id") int orderId) {
        try {
            return orderService.get(orderId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("EMPTY_RESULTSET") + Order.class)) {
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
    OrderViewDto update(@Valid @RequestBody OrderCreateDto orderCreateDto, BindingResult result
            , @PathVariable("id") int orderId) {
        controllerUtils.checkValidationViolations(result);
        try {
            return orderService.update(orderCreateDto,orderId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties.getProperty("DATA_INTEGRITY_VIOLATION_FOR_ORDER"))
                    || message.equals(messagesProperties.getProperty("NOT_UNIQUE_ORDER"))) {
                //http status 409
                throw new RestException(HttpStatus.CONFLICT, message);
            } else if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_ORDE_NONEXISTENT"))) {
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
    public void delete(@PathVariable("id") int orderId) {
        try {
            orderService.delete(orderId);
        } catch (ApplicationException e) {
            String message = e.getMessage();
            if (message.equals(messagesProperties
                    .getProperty("FAILED_UPDATE_ORDER_NONEXISTENT"))) {
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
    List<OrderViewDto> getAll() {
        try {
            List<OrderViewDto> list = orderService.getAllOrders();
            if (list.size() == 0) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND,
                        messagesProperties.getProperty("EMPTY_RESULTSET") + Order.class);
            }
            return list;
        } catch (ApplicationException e) {
            //http status 500
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping(USER + ID)
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody
    List<OrderViewDto> getByUserId(@PathVariable("id") int userId) {
        try {
            List<OrderViewDto> list = orderService.getUsersOrders(userId);
            if (list.size() == 0) {
                //http status 404
                throw new RestException(HttpStatus.NOT_FOUND,
                        messagesProperties.getProperty("EMPTY_RESULTSET") + Order.class);
            }
            return list;
        } catch (ApplicationException e) {
            //http status 500
            throw new RestException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
