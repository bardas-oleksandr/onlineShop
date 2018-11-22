package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.ProductCreateDto;

/**
 *
 */
@Service("productValidatorService")
@Profile("test")
public class ProductValidatorService extends AbstractValidatorService<ProductCreateDto> { }