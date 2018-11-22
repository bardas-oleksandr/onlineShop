package ua.levelup.converter.fromdto;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.UserRegisterCreateDto;

/*Класс UserRegisterCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса UserRegisterCreateDto в объекты класса User
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class UserRegisterCreateDtoConverterTest {

    @Autowired
    private UserRegisterCreateDtoConverter userRegisterCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса UserRegisterCreateDto в объект класса User;
    *           объект UserRegisterCreateDto userRegisterCreateDto не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenUserRegisterCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name","password",
                "mail@gmail.com",User.UserState.ADMIN.ordinal());
        User expected = new User(dto.getUserName(),dto.getPassword(), dto.getEmail()
                , User.UserState.ADMIN);
        //WHEN
        User user = userRegisterCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(user);
        Assert.assertEquals(expected, user);
    }

    /*Сценарий: преобразование объекта класса UserRegisterCreateDto в объект класса User;
    *           объект UserRegisterCreateDto userRegisterCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenUserRegisterCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("userRegisterCreateDto is marked @NonNull but is null");
        //THEN
        userRegisterCreateDtoConverter.convert(null);
    }
}