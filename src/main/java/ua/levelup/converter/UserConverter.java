package ua.levelup.converter;

import lombok.NonNull;
import ua.levelup.model.User;
import ua.levelup.web.dto.UserDto;

public enum UserConverter {
    ;

    public static UserDto asUserViewDto(@NonNull User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setEmail(user.getEmail());
        userDto.setState(user.getUserState().ordinal());
        return userDto;
    }
}