package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.servlet.support.SearchParams;

@Service("searchParamsValidatorService")
public class SearchParamsValidatorService extends AbstractValidatorService<SearchParams> { }