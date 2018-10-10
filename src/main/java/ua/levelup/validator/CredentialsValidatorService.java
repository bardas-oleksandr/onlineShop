package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.CredentialsCreateDto;

/**
 *
 */
@Service("credentialsValidatorService")
public class CredentialsValidatorService extends AbstractValidatorService<CredentialsCreateDto> { }