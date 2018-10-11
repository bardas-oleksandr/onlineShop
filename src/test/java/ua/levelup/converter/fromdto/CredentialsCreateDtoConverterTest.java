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
import ua.levelup.model.Credentials;
import ua.levelup.web.dto.create.CredentialsCreateDto;

/*Класс CredentialsCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса CredentialsCreateDto в объекты класса Credentials
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class CredentialsCreateDtoConverterTest {

    @Autowired
    private CredentialsCreateDtoConverter credentialsCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса CredentialsCreateDto в объект класса Credentials;
    *           объект CredentialsCreateDto credentialsCreateDto не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenCategoryCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("category", "mail@gmail.com");
        Credentials expected = new Credentials(dto.getPassword(), dto.getEmail());
        //WHEN
        Credentials credentials = credentialsCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(credentials);
        Assert.assertEquals(expected, credentials);
    }

    /*Сценарий: преобразование объекта класса CredentialsCreateDto в объект класса Credentials;
    *           объект CredentialsCreateDto credentialsCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenCredentialsCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("credentialsCreateDto is marked @NonNull but is null");
        //THEN
        credentialsCreateDtoConverter.convert(null);
    }
}