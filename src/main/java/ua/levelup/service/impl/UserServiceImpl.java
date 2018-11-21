package ua.levelup.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ua.levelup.dao.UserDao;
import ua.levelup.model.User;
import ua.levelup.service.UserService;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.create.UserWithoutPasswordCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import java.util.ArrayList;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private ConversionService conversionService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User registerUser(UserCreateDto createDto) {
        createDto.setPassword(bCryptPasswordEncoder.encode(createDto.getPassword()));
        User user = conversionService.convert(createDto, User.class);
        userDao.add(user);
        return user;
    }

    @Override
    public UserViewDto updateUser(UserCreateDto userCreateDto, int userId) {
        User user = conversionService.convert(userCreateDto, User.class);
        user.setId(userId);
        userDao.update(user);
        return conversionService.convert(user, UserViewDto.class);
    }

    @Override
    public UserViewDto updateUserWithoutPassword(UserWithoutPasswordCreateDto userCreateDto
            , int userId) {
        User user = conversionService.convert(userCreateDto, User.class);
        user.setId(userId);
        userDao.updateWithoutPassword(user);
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

    @Override
    public List<UserViewDto> getAllUsers() {
        List<User> userList = userDao.getAllUsers();
        List<UserViewDto> viewDtos = new ArrayList<>();
        userList.stream().forEach((user) -> viewDtos
                .add(conversionService.convert(user, UserViewDto.class)));
        return viewDtos;
    }
}