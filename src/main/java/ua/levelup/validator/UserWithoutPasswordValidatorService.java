package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.UserWithoutPasswordCreateDto;

/**
 *
 */
@Service("userWithoutPasswordValidatorService")
public class UserWithoutPasswordValidatorService
        extends AbstractValidatorService<UserWithoutPasswordCreateDto> {
}
