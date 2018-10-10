package ua.levelup.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.web.dto.create.ManufacturerCreateDto;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс ManufacturerValidatorServiceTest содержит интеграционные тесты
 * для проверки корректности валидации объектов ManufacturerCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class ManufacturerValidatorServiceTest {

    @Autowired
    private ManufacturerValidatorService manufacturerValidatorService;

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           все поля корректны.
    * Дано:
    *   - ManufacturerCreateDto dto
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_ManufacturerValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto("manufacturer");
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = manufacturerValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           поле String name == null.
    * Дано:
    *   - ManufacturerCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameEqualsNull_thenNotValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto(null);
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = manufacturerValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_manufacturer_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           поле String name == "".
    * Дано:
    *   - ManufacturerCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsEmpty_thenNotValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto("");
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = manufacturerValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_manufacturer_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ManufacturerCreateDto;
    *           поле String name имеет длину более 50 символов.
    * Дано:
    *   - ManufacturerCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsTooLong_thenNotValidated() {
        //GIVEN
        ManufacturerCreateDto dto = new ManufacturerCreateDto("123456789012345678901234567890123" +
                "456789012345678901");
        //WHEN
        Set<ConstraintViolation<ManufacturerCreateDto>> violations = manufacturerValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_manufacturer_name_length",violations.stream()
                .findFirst().get().getMessage());
    }
}