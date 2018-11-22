package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.CategoryCreateDto;

/**
 *
 */
@Service("categoryValidatorService")
@Profile("test")
public class CategoryValidatorService extends AbstractValidatorService<CategoryCreateDto> { }