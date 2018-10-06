package ua.levelup.converter.fromCreateDto;

import com.sun.istack.internal.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserCreateDto;

public class UserCreateDtoConverter implements Converter<UserCreateDto, User> {

    @NotNull
    @Override
    public User convert(@NonNull UserCreateDto userCreateDto) {
        User user = new User();
        user.setUserName(userCreateDto.getUserName());
        user.setPassword(userCreateDto.getPassword());
        user.setEmail(userCreateDto.getEmail());
        return user;
    }
}
