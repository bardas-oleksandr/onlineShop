package ua.levelup.web.dto.create;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Класс ManufacturerCreateDtoTest содержит интеграционные тесты
 * для проверки корректности валидации объектов ManufacturerCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ManufacturerCreateDtoTest {

    @Autowired
    private Validator validator;

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_ManufacturerValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto("manufacturer");
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = validator
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           поле String name == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameEqualsNull_thenNotValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto(null);
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = validator
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_manufacturer_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           поле String name == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsEmpty_thenNotValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto("");
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = validator
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_manufacturer_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           поле String name имеет длину более 50 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsTooLong_thenNotValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto("123456789012345678901234567890123" +
                "456789012345678901");
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = validator
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_manufacturer_name_length",violations.stream()
                .findFirst().get().getMessage());
    }
}