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

import javax.validation.Valid;
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
    public User registerUser(@Valid UserCreateDto createDto) {
        createDto.setPassword(bCryptPasswordEncoder.encode(createDto.getPassword()));
        User user = conversionService.convert(createDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userDao.add(user);
        return user;
    }

    @Override
    public UserViewDto updateUser(@Valid UserCreateDto userCreateDto, int userId) {
        User user = conversionService.convert(userCreateDto, User.class);
        user.setId(userId);
        User original = userDao.getById(userId);
        user.setPassword(original.getPassword());
        userDao.update(user);
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