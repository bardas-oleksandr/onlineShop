package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserRegisterCreateDto;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import java.util.List;

public interface UserService {
    User registerUser(UserRegisterCreateDto createDto);
    UserViewDto updateUser(UserCreateDto userCreateDto, int userId);
    UserViewDto getUserViewDto(String email);
    User getUser(String email);
    List<UserViewDto> getAllUsers();
}