package ua.levelup.validator;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.UserCreateDto;

/**
 *
 */
@Service("userValidatorService")
@Profile("test")
public class UserValidatorService extends AbstractValidatorService<UserCreateDto> {
}