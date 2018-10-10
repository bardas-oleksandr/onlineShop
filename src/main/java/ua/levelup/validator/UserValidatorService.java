package ua.levelup.validator;

import org.springframework.stereotype.Service;
import ua.levelup.web.dto.create.UserCreateDto;

/**
 *
 */
@Service("userValidatorService")
public class UserValidatorService extends AbstractValidatorService<UserCreateDto> { }