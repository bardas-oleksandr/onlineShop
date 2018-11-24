package ua.levelup.web.dto.create;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.validator.OrderPositionValidatorService;

import javax.validation.ConstraintViolation;
import java.util.Set;

/**
 * Класс OrderPositionCreateDtoTest содержит интеграционные тесты
 * для проверки корректности валидации объектов OrderPositionCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class OrderPositionCreateDtoTest {

    @Autowired
    private OrderPositionValidatorService orderPositionValidatorService;

    /*Сценарий: валидация объекта OrderPositionCreateDto;
    *           все поля корректны.
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_OrderPositionValidated() {
        //GIVEN
        OrderPositionCreateDto dto = new OrderPositionCreateDto(1,2,3,4.0f);
        //WHEN
        Set<ConstraintViolation<OrderPositionCreateDto>> violations = orderPositionValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта OrderPositionCreateDto;
    *           поле int orderId < 1.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenOrderIdSmallerThenOne_thenNotValidated() {
        //GIVEN
        OrderPositionCreateDto dto = new OrderPositionCreateDto(0,2,3,4.0f);
        //WHEN
        Set<ConstraintViolation<OrderPositionCreateDto>> violations = orderPositionValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_order_id",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderPositionCreateDto;
    *           поле int productId < 1.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenProductIdSmallerThenOne_thenNotValidated() {
        //GIVEN
        OrderPositionCreateDto dto = new OrderPositionCreateDto(1,0,3,4.0f);
        //WHEN
        Set<ConstraintViolation<OrderPositionCreateDto>> violations = orderPositionValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_product_id",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderPositionCreateDto;
    *           поле int quantity < 1.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenQuantitySmallerThenOne_thenNotValidated() {
        //GIVEN
        OrderPositionCreateDto dto = new OrderPositionCreateDto(1,2,0,4.0f);
        //WHEN
        Set<ConstraintViolation<OrderPositionCreateDto>> violations = orderPositionValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_product_quantity",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderPositionCreateDto;
    *           поле float price < 0.
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPriceIsNegative_thenNotValidated() {
        //GIVEN
        OrderPositionCreateDto dto = new OrderPositionCreateDto(1,2,3,-0.1f);
        //WHEN
        Set<ConstraintViolation<OrderPositionCreateDto>> violations = orderPositionValidatorService
                .validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_price",violations.stream()
                .findFirst().get().getMessage());
    }
}