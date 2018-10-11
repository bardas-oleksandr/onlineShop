package ua.levelup.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.web.dto.create.CredentialsCreateDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс CredentialsValidatorServiceTest содержит интеграционные тесты
 * для проверки корректности валидации объектов CredentialsCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class CredentialsValidatorServiceTest {

    @Autowired
    private CredentialsValidatorService credentialsValidatorService;

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_CredentialsValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("password", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           поле String password == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordEqualsNull_thenNotValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto(null, "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_password",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           поле String password == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsEmpty_thenNotValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           поле String password короче 6 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsTooShort_thenNotValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("12345", "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           поле String password длинее 20 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsTooLong_thenNotValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("01234556789012345567890",
                "mail@gmail.com");
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           поле String email имеет неверный формат.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailInvalid_thenNotValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("password", "mailgmail.com");
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("wrong_email_format",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           поле String email == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailEqualsNull_thenNotValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("password", null);
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_email",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CredentialsCreateDto;
    *           поле String email == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailIsEmpty_thenNotValidated() {
        //GIVEN
        CredentialsCreateDto dto = new CredentialsCreateDto("password", "");
        //WHEN
        Set<ConstraintViolation<CredentialsCreateDto>> violations = credentialsValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_email",violations.stream()
                .findFirst().get().getMessage());
    }
}