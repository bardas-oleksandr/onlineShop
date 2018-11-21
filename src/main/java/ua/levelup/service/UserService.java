package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import javax.validation.Valid;
import java.util.List;

public interface UserService {
    UserViewDto registerUser(@Valid UserCreateDto createDto);
    UserViewDto updateUser(@Valid UserCreateDto userCreateDto, int userId);
    UserViewDto getUserViewDto(String email);
    User getUser(String email);
    List<UserViewDto> getAllUsers();
}