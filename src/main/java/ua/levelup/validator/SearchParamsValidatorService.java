package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.SearchParamsDto;

/**
 *
 */
@Service("searchParamsValidatorService")
public class SearchParamsValidatorService extends AbstractValidatorService<SearchParamsDto> { }