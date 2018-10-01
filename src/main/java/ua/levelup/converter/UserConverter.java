package ua.levelup.converter;

import ua.levelup.model.User;
import ua.levelup.web.dto.UserDto;

public enum UserConverter {
    ;

    public static UserDto asUserViewDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setState(user.getUserState().ordinal());
        return userDto;
    }
}