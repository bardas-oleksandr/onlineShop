package ua.levelup.converter.fromdto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserCreateDto;

/**
 *
 */
@Component("userCreateDtoConverter")
public class UserCreateDtoConverter implements Converter<UserCreateDto, User> {

    @Override
    public User convert(@NonNull UserCreateDto userCreateDto) {
        User user = new User();
        user.setUserName(userCreateDto.getUserName());
        user.setEmail(userCreateDto.getEmail());
        user.setUserState(User.UserState.get(userCreateDto.getUserStateIndex()));
        return user;
    }
}
