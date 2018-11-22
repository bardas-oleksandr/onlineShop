package ua.levelup.converter.fromdto;

import org.springframework.core.convert.converter.Converter;
import lombok.NonNull;
import org.springframework.stereotype.Component;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserRegisterCreateDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("userRegisterCreateDtoConverter")
public class UserRegisterCreateDtoConverter implements Converter<UserRegisterCreateDto, User> {

    @Override
    public User convert(@NonNull UserRegisterCreateDto userRegisterCreateDto) {
        User user = new User();
        user.setUserName(userRegisterCreateDto.getUserName());
        user.setPassword(userRegisterCreateDto.getPassword());
        user.setEmail(userRegisterCreateDto.getEmail());
        user.setUserState(User.UserState.get(userRegisterCreateDto.getUserStateIndex()));
        return user;
    }
}