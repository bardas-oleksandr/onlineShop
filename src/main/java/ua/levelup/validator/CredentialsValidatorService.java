package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.CredentialsCreateDto;

/**
 *
 */
@Service("credentialsValidatorService")
@Profile("test")
public class CredentialsValidatorService extends AbstractValidatorService<CredentialsCreateDto> { }