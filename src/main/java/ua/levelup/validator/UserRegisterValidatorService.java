package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.UserRegisterCreateDto;

/**
 *
 */
@Service("userRegisterValidatorService")
public class UserRegisterValidatorService extends AbstractValidatorService<UserRegisterCreateDto> { }