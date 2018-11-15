package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.web.dto.view.UserViewDto;

public interface SecurityService {
    boolean isCorrectPassword(User user, String password);
    boolean isAccessAllowed(UserViewDto user, int id);
}
