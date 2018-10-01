package ua.levelup.service;

import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.UserDto;

public interface UserService {
    UserDto registerUser(UserCreateDto user);
    UserDto login(String email, String password);
}
