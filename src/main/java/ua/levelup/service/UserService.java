package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

public interface UserService {
    UserViewDto registerUser(UserCreateDto createDto);
    User getUser(String email);
    UserViewDto getUserViewDto(String email);
    //UserViewDto login(CredentialsCreateDto createDto);
}