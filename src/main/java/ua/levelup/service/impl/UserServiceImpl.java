package ua.levelup.service.impl;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levelup.converter.toViewDto.UserConverter;
import ua.levelup.dao.UserDao;
import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.User;
import ua.levelup.service.SecurityService;
import ua.levelup.service.UserService;
import ua.levelup.validator.UserValidator;
import ua.levelup.web.dto.view.UserViewDto;

@Service("userService")
@Getter
@Setter
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @Override
    public UserViewDto registerUser(User user) {
        UserValidator.validateNewUser(user);
        user = userDao.add(user);
        return UserConverter.asUserViewDto(user);
    }

    @Override
    public UserViewDto login(String email, String password) {
        UserValidator.validateUsersCredentials(email, password);
        User user = userDao.getByEmail(email);
        if(!securityService.isCorrectPassword(user, password)){
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("WRONG_PASSWORD"));
        }
        return UserConverter.asUserViewDto(user);
    }
}
