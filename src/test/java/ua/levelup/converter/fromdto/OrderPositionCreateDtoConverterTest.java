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
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.create.OrderPositionCreateDto;

/*Класс OrderPositionCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса OrderPositionCreateDto в объекты класса OrderPosition
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class OrderPositionCreateDtoConverterTest {

    @Autowired
    private OrderPositionCreateDtoConverter orderPositionCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса OrderPositionCreateDto в объект класса OrderPosition;
    *           объект OrderPositionCreateDto orderPositionCreateDto не равен null.
    * Дано:
    *   - OrderPositionCreateDto orderPositionCreateDto
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenOrderPositionCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        OrderPositionCreateDto dto = new OrderPositionCreateDto(1,2,
                "product", 3,4.0f);
        OrderPosition expected = new OrderPosition(dto.getOrderId(),dto.getProductId(),
                dto.getProductName(),dto.getQuantity(),dto.getUnitPrice());
        //WHEN
        OrderPosition orderPosition = orderPositionCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(orderPosition);
        Assert.assertEquals(expected, orderPosition);
    }

    /*Сценарий: преобразование объекта класса OrderPositionCreateDto в объект класса OrderPosition;
    *           объект OrderPositionCreateDto orderPositionCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenOrderPositionCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("orderPositionCreateDto is marked @NonNull but is null");
        //THEN
        orderPositionCreateDtoConverter.convert(null);
    }
}