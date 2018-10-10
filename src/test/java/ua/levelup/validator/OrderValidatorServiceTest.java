package ua.levelup.validator;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.web.dto.create.OrderCreateDto;

import javax.validation.ConstraintViolation;
import java.sql.Timestamp;
import java.util.Set;

/**
 * Класс OrderValidatorServiceTest содержит интеграционные тесты
 * для проверки корректности валидации объектов OrderCreateDto
 *
 * Автор: Бардась А.А.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class OrderValidatorServiceTest {

    @Autowired
    private OrderValidatorService orderValidatorService;

    /*Сценарий: валидация объекта OrderCreateDto;
    *           все поля корректны.
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект успешно валидирован.
    * */
    @Test
    public void validateTest_OrderValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1,"address",new Timestamp(1),
                1);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(0, violations.size());
    }

    /*Сценарий: валидация объекта OrderCreateDto;
    *           поле int userId < 1.
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenUserIdSmallerThenOne_thenNotValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(0,"address",new Timestamp(1),
                1);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_user_id",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderCreateDto;
    *           поле String address == null
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenAddressEqualsNull_thenNotValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1,null,new Timestamp(1),
                1);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_address",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderCreateDto;
    *           поле String address == ""
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenAddressIsEmpty_thenNotValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1,"",new Timestamp(1),
                1);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_address",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderCreateDto;
    *           поле String address имеет длину более 255 символов
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenAddressIsTooLong_thenNotValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1,"012345678901234567890123456789012" +
                "3456789012345678901234567890123456789012345678901234567890123456789012345678901234" +
                "5678901234567890123456789012345678901234567890123456789012345678901234567890123456" +
                "78901234567890123456789012345678901234567890123456789012345",new Timestamp(1),
                1);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unacceptable_address_length",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderCreateDto;
    *           поле TimeStamp date == null
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenDateEqualsNull_thenNotValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1,"address",null,
                1);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("empty_order_date",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderCreateDto;
    *           поле int paymentConditionsIndex < 0
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPaymentConditionsIndexIsNegative_thenNotValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1,"address",new Timestamp(1),
                -1);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unexpected_payment_conditions",violations.stream()
                .findFirst().get().getMessage());
    }

    /*Сценарий: валидация объекта OrderCreateDto;
    *           поле int paymentConditionsIndex > 1
    * Дано:
    *   - OrderCreateDto dto
    * Результат: объект не валидирован.
    * */
    @Test
    public void validateTest_whenPaymentConditionsIndexIsBiggerThenOne_thenNotValidated() {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1,"address",new Timestamp(1),
                2);
        //WHEN
        Set<ConstraintViolation<OrderCreateDto>> violations = orderValidatorService.validate(dto);
        //THEN
        Assert.assertEquals(1, violations.size());
        Assert.assertEquals("unexpected_payment_conditions",violations.stream()
                .findFirst().get().getMessage());
    }
}