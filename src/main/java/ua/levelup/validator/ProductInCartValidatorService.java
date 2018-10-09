package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.ProductInCartCreateDto;

@Service("productInCartValidatorService")
public class ProductInCartValidatorService extends AbstractValidatorService<ProductInCartCreateDto> { }