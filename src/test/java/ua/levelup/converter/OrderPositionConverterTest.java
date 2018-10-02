package ua.levelup.converter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.OrderPositionDto;

/*Класс OrderPositionConverterTest содержит тесты для проверки
* конвертации объектов класса OrderPosition в объекты класса OrderPositionDto
*
* Автор: Бардась А.А.
* */
public class OrderPositionConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса OrderPosition в объект класса OrderPositionDto.
    *           Аргумент метода не равен null
    * Дано:
    *   - OrderPosition orderPosition
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asOrderPositionDtoTest() throws Exception {
        //GIVEN
        OrderPosition orderPosition = new OrderPosition(1,2,"productName",
                3,4.0f);
        //WHEN
        OrderPositionDto orderPositionDto = OrderPositionConverter.asOrderPositionDto(orderPosition);
        //THEN
        Assert.assertNotNull(orderPositionDto);
        Assert.assertEquals(orderPosition.getOrderId(), orderPositionDto.getOrderId());
        Assert.assertEquals(orderPosition.getProductId(), orderPositionDto.getProductId());
        Assert.assertEquals(orderPosition.getProductName(), orderPositionDto.getProductName());
        Assert.assertEquals(orderPosition.getQuantity(), orderPositionDto.getQuantity());
        Assert.assertEquals(orderPosition.getUnitPrice(), orderPositionDto.getUnitPrice(), 0);
    }

    /*Сценарий: преобразование объекта класса OrderPosition в объект класса OrderPositionDto.
    *           Аргумент метода равен null
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asOrderPositionDtoTest_whenOrderPositionNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("orderPosition is marked @NonNull but is null");
        //THEN
        OrderPositionConverter.asOrderPositionDto(null);
    }
}