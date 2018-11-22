package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.OrderPositionCreateDto;

/**
 *
 */
@Service("orderPositionValidatorService")
@Profile("test")
public class OrderPositionValidatorService extends AbstractValidatorService<OrderPositionCreateDto> { }