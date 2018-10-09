package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.CategoryCreateDto;

@Service("categoryValidatorService")
public class CategoryValidatorService extends AbstractValidatorService<CategoryCreateDto> { }