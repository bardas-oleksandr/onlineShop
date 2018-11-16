package ua.levelup.service;

import ua.levelup.model.User;

public interface SecurityUserService {
    User getUser(String email);
}
