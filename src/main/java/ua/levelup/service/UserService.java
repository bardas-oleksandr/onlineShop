package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.UserDto;

public interface UserService {
    UserDto registerUser(User user);
    UserDto login(String email, String password);
}
