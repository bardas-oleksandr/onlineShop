package ua.levelup.converter.todto;

import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import ua.levelup.model.User;
import ua.levelup.web.dto.view.UserViewDto;

/**
 *
 */
//Если пометить класс каждого конвертера как @Component, тогда Spring
//сам соберет все конвертеры в Set<Converter<?,?>> и вручную собирать уже не нужно
@Component("userConverter")
public class UserConverter implements Converter<User, UserViewDto> {

    @Override
    public UserViewDto convert(@NonNull User user) {
        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setId(user.getId());
        userViewDto.setUserName(user.getUserName());
        userViewDto.setEmail(user.getEmail());
        userViewDto.setState(user.getUserState().ordinal());
        return userViewDto;
    }
}