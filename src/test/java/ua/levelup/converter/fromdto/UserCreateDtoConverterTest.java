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
import ua.levelup.web.dto.create.UserCreateDto;

/*Класс UserCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса UserCreateDto в объекты класса User
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class UserCreateDtoConverterTest {

    @Autowired
    private UserCreateDtoConverter userCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса UserCreateDto в объект класса User;
    *           объект UserCreateDto userCreateDto не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenUserCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","password",
                "mail@gmail.com");
        User expected = new User(dto.getUserName(),dto.getPassword(), dto.getEmail());
        //WHEN
        User user = userCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(user);
        Assert.assertEquals(expected, user);
    }

    /*Сценарий: преобразование объекта класса UserCreateDto в объект класса User;
    *           объект UserCreateDto userCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenUserCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("userCreateDto is marked @NonNull but is null");
        //THEN
        userCreateDtoConverter.convert(null);
    }
}