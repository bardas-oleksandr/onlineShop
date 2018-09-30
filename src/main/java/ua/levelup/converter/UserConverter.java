package ua.levelup.converter;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.User;
import ua.levelup.web.dto.UserCreateDto;
import ua.levelup.web.dto.UserViewDto;

public enum UserConverter {
    ;

    public static User asUser(UserCreateDto createdUser){
        if(createdUser.getUserName() == null ||
                createdUser.getEmail() == null ||
                createdUser.getPassword() == null ||
                createdUser.getState() == null){
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("AS_USER_CONVERTATION_ERROR"));
        }
        User user = new User();
        user.setUserName(createdUser.getUserName());
        user.setPassword(createdUser.getPassword());
        user.setEmail(createdUser.getEmail());
        user.setUserState(createdUser.getState().ordinal());
        return user;
    }

    public static UserViewDto asUserViewDto(User user){
        if(user.getUserName() == null ||
                user.getEmail() == null ||
                user.getPassword() == null ||
                user.getUserState() == null){
            throw new ApplicationException(MessageHolder.getMessageProperties()
                    .getProperty("AS_USER_VIEW_DTO_CONVERTATION_ERROR"));
        }
        UserViewDto viewDto = new UserViewDto();
        viewDto.setId(user.getId());
        viewDto.setUserName(user.getUserName());
        viewDto.setEmail(user.getEmail());
        viewDto.setState(user.getUserState().ordinal());
        return viewDto;
    }
}
