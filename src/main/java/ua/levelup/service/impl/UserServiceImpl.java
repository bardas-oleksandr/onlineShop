package ua.levelup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import ua.levelup.dao.UserDao;
import ua.levelup.exception.ValidationException;
import ua.levelup.model.Credentials;
import ua.levelup.model.User;
import ua.levelup.service.SecurityService;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.CredentialsCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private ConversionService conversionService;

    @Override
    public UserViewDto registerUser(User user) {
        userDao.add(user);
        return conversionService.convert(user, UserViewDto.class);
    }

    @Override
    public UserViewDto login(CredentialsCreateDto createDto) {
        Credentials credentials = conversionService.convert(createDto, Credentials.class);
        User user = userDao.getByEmail(credentials.getEmail());

        if (securityService.isCorrectPassword(user, credentials.getPassword())){
            return conversionService.convert(user, UserViewDto.class);
        }else{
            throw new ValidationException("wrong_password");
        }
    }
}