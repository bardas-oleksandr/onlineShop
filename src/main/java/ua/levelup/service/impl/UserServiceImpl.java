package ua.levelup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.levelup.dao.UserDao;
import ua.levelup.model.User;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserViewDto registerUser(UserCreateDto createDto) {
        User user = conversionService.convert(createDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return conversionService.convert(user, UserViewDto.class);
    }

    @Override
    public UserViewDto getUserViewDto(String email) {
        User user = userDao.getByEmail(email);
        return conversionService.convert(user, UserViewDto.class);
    }

    @Override
    public User getUser(String email) {
        return userDao.getByEmail(email);
    }
}