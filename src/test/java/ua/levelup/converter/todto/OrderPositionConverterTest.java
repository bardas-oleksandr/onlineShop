package ua.levelup.converter.todto;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.model.OrderPosition;
import ua.levelup.web.dto.view.OrderPositionViewDto;

/*Класс OrderPositionConverterTest содержит тесты для проверки
* конвертации объектов класса OrderPosition в объекты класса OrderPositionViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class OrderPositionConverterTest {

    @Autowired
    private OrderPositionConverter orderPositionConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса OrderPosition в объект класса OrderPositionViewDto;
    *           объект OrderPosition orderPosition не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenOrderPositionNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        OrderPosition orderPosition = new OrderPosition(1,2,3,4.0f);
        orderPosition.setProductName("product");
        OrderPositionViewDto expected = new OrderPositionViewDto();
        expected.setOrderId(orderPosition.getOrderId());
        expected.setProductId(orderPosition.getProductId());
        expected.setProductName(orderPosition.getProductName());
        expected.setQuantity(orderPosition.getQuantity());
        expected.setUnitPrice(orderPosition.getUnitPrice());
        //WHEN
        OrderPositionViewDto orderPositionViewDto = orderPositionConverter.convert(orderPosition);
        //THEN
        Assert.assertNotNull(orderPositionViewDto);
        Assert.assertEquals(expected, orderPositionViewDto);
    }

    /*Сценарий: преобразование объекта класса OrderPosition в объект класса OrderPositionViewDto;
    *           объект OrderPosition orderPosition равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenOrderPositionEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("orderPosition is marked @NonNull but is null");
        //THEN
        orderPositionConverter.convert(null);
    }
}