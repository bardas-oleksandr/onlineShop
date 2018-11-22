package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.OrderCreateDto;

/**
 *
 */
@Service("orderValidatorService")
@Profile("test")
public class OrderValidatorService extends AbstractValidatorService<OrderCreateDto> { }