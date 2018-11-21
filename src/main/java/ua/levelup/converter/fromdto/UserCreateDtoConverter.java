package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserCreateDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("userCreateDtoConverter")
public class UserCreateDtoConverter implements Converter<UserCreateDto, User> {

    @Override
    public User convert(@NonNull UserCreateDto userCreateDto) {
        User user = new User();
        user.setUserName(userCreateDto.getUserName());
        user.setPassword(userCreateDto.getPassword());
        user.setEmail(userCreateDto.getEmail());
        user.setUserState(User.UserState.get(userCreateDto.getUserStateIndex()));
        return user;
    }
}