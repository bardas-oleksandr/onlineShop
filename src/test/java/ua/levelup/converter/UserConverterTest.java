package ua.levelup.converter;

import ua.levelup.converter.toViewDto.UserConverter;
import ua.levelup.model.User;
import ua.levelup.model.support.UserState;
import ua.levelup.web.dto.view.UserViewDto;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*Класс UserConverterTest содержит тесты для проверки
* конвертации объектов класса User в объекты класса UserDto
*
* Автор: Бардась А.А.
* */
public class UserConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса User в объект класса UserDto.
    *           Аргумент метода не равен null
    * Дано:
    *   - User user
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asUserDtoTest() throws Exception {
        //GIVEN
        User user = new User("name", "password",
                "email@gmail.com", UserState.ACTIVE.ordinal());
        UserViewDto expected = new UserViewDto();
        expected.setUserName(user.getUserName());
        expected.setEmail(user.getEmail());
        expected.setState(user.getUserState().ordinal());
        //WHEN
        UserViewDto userDto = UserConverter.asUserViewDto(user);
        //THEN
        Assert.assertNotNull(userDto);
        Assert.assertEquals(expected, userDto);
    }

    /*Сценарий: преобразование объекта класса User в объект класса UserDto.
    *           Аргумент метода равен null
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asUserDtoTest_whenUserNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("user is marked @NonNull but is null");
        //THEN
        UserConverter.asUserViewDto(null);
    }
}