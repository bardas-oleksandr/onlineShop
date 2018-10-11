package ua.levelup.converter.fromdto;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.model.Order;
import ua.levelup.model.User;
import ua.levelup.web.dto.create.OrderCreateDto;

import java.sql.Timestamp;

/*Класс OrderCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса OrderCreateDto в объекты класса Order
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class OrderCreateDtoConverterTest {

    @Autowired
    private OrderCreateDtoConverter orderCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса OrderCreateDto в объект класса Order;
    *           объект OrderCreateDto orderCreateDto не равен null.
    * Дано:
    *   - OrderCreateDto orderCreateDto
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenOrderCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        OrderCreateDto dto = new OrderCreateDto(1, "address",
                new Timestamp(1), 1);
        User user = new User();
        user.setId(dto.getUserId());
        Order expected = new Order(user, dto.getAddress(), dto.getDate(),
                Order.PaymentConditions.get(dto.getPaymentConditionsIndex()));
        //WHEN
        Order order = orderCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(order);
        Assert.assertEquals(expected, order);
    }

    /*Сценарий: преобразование объекта класса OrderCreateDto в объект класса Order;
    *           объект OrderCreateDto orderCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenOrderCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("orderCreateDto is marked @NonNull but is null");
        //THEN
        orderCreateDtoConverter.convert(null);
    }
}