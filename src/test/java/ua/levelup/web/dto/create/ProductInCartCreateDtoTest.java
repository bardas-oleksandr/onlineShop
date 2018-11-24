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
 * Класс ProductInCartCreateDtoTest содержит интеграционные тесты
 * для проверки корректности валидации объектов ProductInCartCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ProductInCartCreateDtoTest {

    @Autowired
    private Validator validator;

    /*Сценарий: валидация объекта ProductInCartCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_ProductInCartValidated() {
        //GIVEN
        ProductInCartCreateDto dto = new ProductInCartCreateDto(1,2);
        //WHEN
        Set<ConstraintViolation<ProductInCartCreateDto>> violations = validator
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта ProductInCartCreateDto;
    *           поле int productId < 1.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenProductIdSmallerThenOne_thenNotValidated() {
        //GIVEN
        ProductInCartCreateDto dto = new ProductInCartCreateDto(0,2);
        //WHEN
        Set<ConstraintViolation<ProductInCartCreateDto>> violations = validator
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_product_id",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта ProductInCartCreateDto;
    *           поле int quantity < 1.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenQuantitySmallerThenOne_thenNotValidated() {
        //GIVEN
        ProductInCartCreateDto dto = new ProductInCartCreateDto(1,0);
        //WHEN
        Set<ConstraintViolation<ProductInCartCreateDto>> violations = validator
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_product_quantity",violations.stream()
                .findFirst().get().getMessage());
    }
}