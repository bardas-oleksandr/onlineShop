//package ua.levelup.service.impl;
//
//import lombok.Setter;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.core.convert.ConversionService;
//import org.springframework.stereotype.Service;
//import ua.levelup.dao.UserDao;
//import ua.levelup.exception.ValidationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.model.User;
//import ua.levelup.service.SecurityService;
//import ua.levelup.service.UserService;
//import ua.levelup.validator.UserValidator;
//import ua.levelup.web.dto.view.UserViewDto;
//
//@Setter
//@Service("userService")
//public class UserServiceImpl implements UserService {
//
//    @Autowired
//    private UserDao userDao;
//
//    @Autowired
//    private SecurityService securityService;
//
//    @Autowired
//    ConversionService conversionService;
//
//    @Override
//    public UserViewDto registerUser(User user) {
//        UserValidator.validateNewUser(user);
//        user = userDao.add(user);
//        return conversionService.convert(user, UserViewDto.class);
//    }
//
//    @Override
//    public UserViewDto login(String email, String password) {
//        UserValidator.validateUsersCredentials(email, password);
//        User user = userDao.getByEmail(email);
//        if(!securityService.isCorrectPassword(user, password)){
//            throw new ValidationException(MessageContainer.getMessageProperties()
//                    .getProperty("WRONG_PASSWORD"));
//        }
//        return conversionService.convert(user, UserViewDto.class);
//    }
//}