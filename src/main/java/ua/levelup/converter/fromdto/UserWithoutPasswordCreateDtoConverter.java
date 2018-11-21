package ua.levelup.converter.fromdto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserWithoutPasswordCreateDto;

/**
 *
 */
@Component("userWithoutPasswordCreateDtoConverter")
public class UserWithoutPasswordCreateDtoConverter implements Converter<UserWithoutPasswordCreateDto, User> {

    @Override
    public User convert(@NonNull UserWithoutPasswordCreateDto source) {
        User user = new User();
        user.setUserName(source.getUserName());
        user.setEmail(source.getEmail());
        user.setUserState(User.UserState.get(source.getUserStateIndex()));
        return user;
    }
}
