package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.create.UserWithoutPasswordCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import java.util.List;

public interface UserService {
    User registerUser(UserCreateDto createDto);
    UserViewDto updateUser(UserCreateDto userCreateDto, int userId);
    UserViewDto updateUserWithoutPassword(UserWithoutPasswordCreateDto userCreateDto
            , int userId);
    UserViewDto getUserViewDto(String email);
    User getUser(String email);
    List<UserViewDto> getAllUsers();
}