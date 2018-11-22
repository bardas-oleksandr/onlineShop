package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.ProductInCartCreateDto;

/**
 *
 */
@Service("productInCartValidatorService")
@Profile("test")
public class ProductInCartValidatorService extends AbstractValidatorService<ProductInCartCreateDto> { }