package ua.levelup.converter.todto;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.model.User;
import ua.levelup.web.dto.view.UserViewDto;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/*Класс UserConverterTest содержит тесты для проверки
* конвертации объектов класса User в объекты класса UserViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class UserConverterTest {

    @Autowired
    private UserConverter userConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса User в объект класса UserViewDto;
    *           объект User user не равен null.
    * Дано:
    *   - User user
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenUserNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        User user = new User("name", "password",
                "email@gmail.com", User.UserState.ACTIVE);
        UserViewDto expected = new UserViewDto();
        expected.setUserName(user.getUserName());
        expected.setEmail(user.getEmail());
        expected.setState(user.getUserState().ordinal());
        //WHEN
        UserViewDto userViewDto = userConverter.convert(user);
        //THEN
        Assert.assertNotNull(userViewDto);
        Assert.assertEquals(expected, userViewDto);
    }

    /*Сценарий: преобразование объекта класса User в объект класса UserViewDto;
    *           объект User user равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenUserEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("user is marked @NonNull but is null");
        //THEN
        userConverter.convert(null);
    }
}