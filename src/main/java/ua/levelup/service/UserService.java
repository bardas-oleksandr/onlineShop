package ua.levelup.service;

import ua.levelup.web.dto.UserCreateDto;
import ua.levelup.web.dto.UserViewDto;

public interface UserService {
    UserViewDto registerUser(UserCreateDto user);
    UserViewDto login(String email, String password);
}
