package ua.levelup.service;

import ua.levelup.model.User;

public interface SecurityService {
    boolean isCorrectPassword(User user, String password);
}
