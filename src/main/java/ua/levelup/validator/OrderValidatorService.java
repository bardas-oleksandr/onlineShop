package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.OrderCreateDto;

/**
 *
 */
@Service("orderValidatorService")
public class OrderValidatorService extends AbstractValidatorService<OrderCreateDto> { }