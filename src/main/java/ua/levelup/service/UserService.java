package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import java.util.List;

public interface UserService {
    UserViewDto registerUser(UserCreateDto createDto);
    UserViewDto getUserViewDto(String email);
    User getUser(String email);
    List<UserViewDto> getAllUsers();
}