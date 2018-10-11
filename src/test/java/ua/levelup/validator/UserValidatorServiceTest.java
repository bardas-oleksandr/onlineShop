package ua.levelup.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.web.dto.create.UserCreateDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс UserValidatorServiceTest содержит интеграционные тесты
 * для проверки корректности валидации объектов UserCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class UserValidatorServiceTest {

    @Autowired
    private UserValidatorService userValidatorService;

    /*Сценарий: валидация объекта UserCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_CredentialsValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","password","mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String password == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordEqualsNull_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name", null, "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_password",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String password == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsEmpty_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String password короче 6 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsTooShort_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name", "12345", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String password длинее 20 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsTooLong_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","01234556789012345567890",
                "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String email имеет неверный формат.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailInvalid_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","password", "mailgmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("wrong_email_format",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String email == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailEqualsNull_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","password", null);
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_email",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String email == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailIsEmpty_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","password", "");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_email",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String name == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameEqualsNull_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto(null,"password", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_username",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String name == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsEmpty_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("","password", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_username",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String name имеет длину более 20 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsTooLong_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("Остап-Сулейман-Берта-Мария-Бендер-бей","password", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_username_length",violations.stream()
                .findFirst().get().getMessage());
    }
}