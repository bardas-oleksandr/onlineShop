package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.ProductCreateDto;

/**
 *
 */
@Service("productValidatorService")
public class ProductValidatorService extends AbstractValidatorService<ProductCreateDto> { }