package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.SearchParamsCreateDto;

/**
 *
 */
@Service("searchParamsValidatorService")
public class SearchParamsValidatorService extends AbstractValidatorService<SearchParamsCreateDto> { }