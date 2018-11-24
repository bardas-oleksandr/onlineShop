package ua.levelup.web.dto.create;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.model.User;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.validator.UserValidatorService;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс UserCreateDtoTest содержит интеграционные тесты
 * для проверки корректности валидации объектов UserCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class UserCreateDtoTest {

    @Autowired
    private UserValidatorService userValidatorService;

    /*Сценарий: валидация объекта UserCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_ObjectIsValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","mail@gmail.com"
                , User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле String email имеет неверный формат.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenEmailInvalid_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name","mailgmail.com"
                , User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
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
        UserCreateDto dto = new UserCreateDto("name",null
                , User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
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
        UserCreateDto dto = new UserCreateDto("name",""
                , User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
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
        UserCreateDto dto = new UserCreateDto(null,"mail@gmail.com"
                , User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
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
        UserCreateDto dto = new UserCreateDto("","mail@gmail.com"
                , User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
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
        UserCreateDto dto = new UserCreateDto("Остап-Сулейман-Берта-Мария-Бендер-бей"
                , "mail@gmail.com", User.UserState.ADMIN.ordinal());
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_username_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле userStateIndex меньше нуля.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenUserStateIndexIsNegative_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name", "mail@gmail.com"
                , -1);
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unexpected_user_state",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта UserCreateDto;
    *           поле userStateIndex больше 2.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenUserStateIndexIsBiggerThenTwo_thenNotValidated() {
        //GIVEN
        UserCreateDto dto = new UserCreateDto("name", "mail@gmail.com"
                , 3);
        //WHEN
        Set<ConstraintViolation<UserCreateDto>> violations = userValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unexpected_user_state",violations.stream()
                .findFirst().get().getMessage());
    }
}