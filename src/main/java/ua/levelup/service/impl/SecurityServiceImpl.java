package ua.levelup.service.impl;

import ua.levelup.model.User;
import ua.levelup.service.SecurityService;

public class SecurityServiceImpl implements SecurityService {
    @Override
    public boolean isCorrectPassword(User user, String password) {
        return user.getPassword().equals(password);
    }
}
