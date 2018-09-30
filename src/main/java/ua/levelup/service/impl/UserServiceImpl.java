package ua.levelup.service.impl;

import ua.levelup.converter.UserConverter;
import ua.levelup.dao.UserDao;
import ua.levelup.dao.support.DaoHolder;
import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.User;
import ua.levelup.service.SecurityService;
import ua.levelup.service.UserService;
import ua.levelup.validator.UserValidator;
import ua.levelup.web.dto.UserCreateDto;
import ua.levelup.web.dto.UserViewDto;

public class UserServiceImpl implements UserService {

    private UserDao userDao = (UserDao) DaoHolder
            .getDaoObject(DaoHolder.USER_DAO);
    private SecurityService securityService = new SecurityServiceImpl();

    @Override
    public UserViewDto registerUser(UserCreateDto userCreateDto) {
        UserValidator.validateNewUser(userCreateDto);
        User user = UserConverter.asUser(userCreateDto);
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
