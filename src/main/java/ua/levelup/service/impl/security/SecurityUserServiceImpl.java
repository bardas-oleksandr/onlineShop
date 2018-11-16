package ua.levelup.service.impl.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.levelup.dao.UserDao;
import ua.levelup.model.User;
import ua.levelup.service.SecurityUserService;

@Service("securityUserService")
public class SecurityUserServiceImpl implements SecurityUserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUser(String email) {
        return userDao.getByEmail(email);
    }
}
