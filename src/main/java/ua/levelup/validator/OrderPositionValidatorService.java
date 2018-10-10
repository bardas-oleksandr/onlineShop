package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.OrderPositionCreateDto;

/**
 *
 */
@Service("orderPositionValidatorService")
public class OrderPositionValidatorService extends AbstractValidatorService<OrderPositionCreateDto> { }