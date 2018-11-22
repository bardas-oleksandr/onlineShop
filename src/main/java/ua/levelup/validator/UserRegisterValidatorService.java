package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.UserRegisterCreateDto;

/**
 *
 */
@Service("userRegisterValidatorService")
@Profile("test")
public class UserRegisterValidatorService extends AbstractValidatorService<UserRegisterCreateDto> { }