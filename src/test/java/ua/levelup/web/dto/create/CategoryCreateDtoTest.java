package ua.levelup.web.dto.create;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.validator.CategoryValidatorService;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс CategoryCreateDtoTest содержит интеграционные тесты
 * для проверки корректности валидации объектов CategoryCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class CategoryCreateDtoTest {

    @Autowired
    private CategoryValidatorService categoryValidatorService;

    /*Сценарий: валидация объекта CategoryCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_CategoryValidated() {
        //GIVEN
        CategoryCreateDto dto = new CategoryCreateDto("category", 1);
        //WHEN
        Set<ConstraintViolation<CategoryCreateDto>> violations = categoryValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта CategoryCreateDto;
    *           поле String name == null.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameEqualsNull_thenNotValidated() {
        //GIVEN
        CategoryCreateDto dto = new CategoryCreateDto(null, 1);
        //WHEN
        Set<ConstraintViolation<CategoryCreateDto>> violations = categoryValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_category_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CategoryCreateDto;
    *           поле String name == "".
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameIsEmpty_thenNotValidated() {
        //GIVEN
        CategoryCreateDto dto = new CategoryCreateDto("", 1);
        //WHEN
        Set<ConstraintViolation<CategoryCreateDto>> violations = categoryValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_category_name",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CategoryCreateDto;
    *           поле String name имеет длину более 50 символов.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNameLengthTooLong_thenNotValidated() {
        //GIVEN
        CategoryCreateDto dto = new CategoryCreateDto("1234567890123456789012345678901" +
                "23456789012345678901", 1);
        //WHEN
        Set<ConstraintViolation<CategoryCreateDto>> violations = categoryValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_category_name_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта CategoryCreateDto;
    *           поле int parentCategoryId имеет имеет отрицательное значение.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenNegativeParentCategoryId_thenNotValidated() {
        //GIVEN
        CategoryCreateDto dto = new CategoryCreateDto("category", -1);
        //WHEN
        Set<ConstraintViolation<CategoryCreateDto>> violations = categoryValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("negative_parent_category_id",violations.stream()
                .findFirst().get().getMessage());
    }
}