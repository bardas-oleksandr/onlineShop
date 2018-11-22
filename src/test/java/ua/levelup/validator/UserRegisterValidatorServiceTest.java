package ua.levelup.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.model.User;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.web.dto.create.UserRegisterCreateDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс UserRegisterValidatorServiceTest содержит интеграционные тесты
 * для проверки корректности валидации объектов UserRegisterCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class UserRegisterValidatorServiceTest {

    @Autowired
    private UserRegisterValidatorService userRegisterValidatorService;

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_ObjectIsValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name","password"
                ,"mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String password == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordEqualsNull_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name", null
                , "mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_password",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String password == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsEmpty_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name",""
                , "mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String password короче 6 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsTooShort_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name", "123"
                , "mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String password длинее 20 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPasswordIsTooLong_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name"
                ,"01234556789012345567890", "mail@gmail.com"
                , User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_password_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String email имеет неверный формат.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailInvalid_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name","password"
                , "mailgmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("wrong_email_format",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String email == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailEqualsNull_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name","password"
                , null, User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_email",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String email == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailIsEmpty_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name","password"
                , "", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_email",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String name == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameEqualsNull_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto(null,"password"
                , "mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_username",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String name == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsEmpty_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("","password"
                , "mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_username",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле String name имеет длину более 20 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsTooLong_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("Остап-Сулейман-Берта-Мария-Бендер-бей"
                ,"password", "mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_username_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле userStateIndex меньше нуля.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenUserStateIndexIsNegative_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name","password"
                , "mail@gmail.com", -1);
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unexpected_user_state",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserRegisterCreateDto;
    *           поле userStateIndex больше 2.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenUserStateIndexIsBiggerThenTwo_thenNotValidated() {
        //GIVEN
        UserRegisterCreateDto dto = new UserRegisterCreateDto("name","password"
                , "mail@gmail.com", 3);
        //WHEN
        Set<ConstraintViolation<UserRegisterCreateDto>> violations = userRegisterValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unexpected_user_state",violations.stream()
                .findFirst().get().getMessage());
    }
}