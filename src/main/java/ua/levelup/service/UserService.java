package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.view.UserViewDto;

public interface UserService {
    UserViewDto registerUser(User user);
    UserViewDto login(String email, String password);
}
