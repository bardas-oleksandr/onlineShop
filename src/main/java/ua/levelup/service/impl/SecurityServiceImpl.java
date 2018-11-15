package ua.levelup.service.impl;

import org.springframework.stereotype.Service;
import ua.levelup.model.User;
import ua.levelup.service.SecurityService;
import ua.levelup.web.dto.view.UserViewDto;

@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Override
    public boolean isCorrectPassword(User user, String password) {
        return user.getPassword().equals(password);
    }

    @Override
    public boolean isAccessAllowed(UserViewDto user, int id) {
        return user.getId() == id;
    }
}