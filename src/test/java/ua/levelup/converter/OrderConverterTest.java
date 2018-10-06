package ua.levelup.converter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.levelup.converter.toViewDto.OrderConverter;
import ua.levelup.model.Order;
import ua.levelup.model.OrderPosition;
import ua.levelup.model.User;
import ua.levelup.model.support.UserState;
import ua.levelup.web.dto.view.OrderViewDto;

import java.sql.Timestamp;

/*Класс OrderConverterTest содержит тесты для проверки
* конвертации объектов класса Order в объекты класса OrderDto
*
* Автор: Бардась А.А.
* */
public class OrderConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса Order в объект класса OrderDto.
    *           Аргумент метода не равен null
    * Дано:
    *   - Order order
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asOrderDtoTest() throws Exception {
        //GIVEN
        User user = new User("name", "password",
                "email@gmail.com", UserState.ACTIVE.ordinal());
        Order order = new Order(user,0,new Timestamp(1),"addres",1);
        order.addOrderPosition(new OrderPosition());
        order.setId(1);
        //WHEN
        OrderViewDto orderDto = OrderConverter.asOrderDto(order);
        //THEN
        Assert.assertNotNull(orderDto);
        Assert.assertEquals(order.getId(), orderDto.getId());
        Assert.assertEquals(order.getUser().getUserName(), orderDto.getUserDto().getUserName());
        Assert.assertEquals(order.getOrderState().ordinal(), orderDto.getOrderState());
        Assert.assertEquals(order.getDate(), orderDto.getDate());
        Assert.assertEquals(order.getAddress(), orderDto.getAddress());
        Assert.assertEquals(order.getPaymentConditions().ordinal(), orderDto.getConditions());
        Assert.assertEquals(order.getOrderPositionList().size(), orderDto.getOrderPositionList().size());
    }

    /*Сценарий: преобразование объекта класса Order в объект класса OrderDto.
    *           Аргумент метода равен null
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asOrderDtoTest_whenOrderNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("order is marked @NonNull but is null");
        //THEN
        OrderConverter.asOrderDto(null);
    }
}