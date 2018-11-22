package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.SearchParamsDto;

/**
 *
 */
@Service("searchParamsValidatorService")
@Profile("test")
public class SearchParamsValidatorService extends AbstractValidatorService<SearchParamsDto> { }