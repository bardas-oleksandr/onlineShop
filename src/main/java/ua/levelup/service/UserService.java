package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserRegisterCreateDto;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.view.UserViewDto;

import java.util.List;

public interface UserService {
    User registerUser(UserRegisterCreateDto createDto);

    UserViewDto create(UserRegisterCreateDto createDto);

    UserViewDto update(UserCreateDto userCreateDto, int userId);

    void delete(int userId);

    UserViewDto getUserViewDtoByEmail(String email);

    UserViewDto getUserViewDtoById(int userId);

    User getUser(String email);

    List<UserViewDto> getAll();
}