package ua.levelup.converter.fromdto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.model.OrderPosition;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.web.dto.view.ProductInCartViewDto;
import ua.levelup.web.dto.view.ProductViewDto;

/*Класс ProductInCartToOrderPositionConverterTest содержит тесты для проверки
* конвертации объектов класса ProductInCartViewDto в объекты класса OrderPosition
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ProductInCartToOrderPositionConverterTest {

    final static int ORDER_ID = 1;
    final static int PRODUCT_ID = 2;
    final static int COUNT = 3;
    final static float PRICE = 2.5f;
    final static String PRODUCT_NAME = "product";
    final static boolean AVAILABLE = true;

    @Autowired
    private ProductInCartToOrderPositionConverter converter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private ProductInCartViewDto productInCartViewDto;

    @Before
    public void init() {
        productInCartViewDto = new ProductInCartViewDto();
        ProductViewDto productViewDto = new ProductViewDto();
        productViewDto.setId(PRODUCT_ID);
        productViewDto.setName(PRODUCT_NAME);
        productViewDto.setAvailable(AVAILABLE);
        productViewDto.setPrice(PRICE);
        productInCartViewDto.setProductViewDto(productViewDto);
        productInCartViewDto.setCount(COUNT);
    }

    /*Сценарий: преобразование объекта класса ProductInCartViewDto в объект класса OrderPosition;
    *           объект ProductInCartViewDto не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenProductInCartNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        OrderPosition expected = new OrderPosition(ORDER_ID, PRODUCT_ID, COUNT, PRICE);
        expected.setProductName(PRODUCT_NAME);
        //WHEN
        OrderPosition orderPosition = converter.convert(productInCartViewDto);
        orderPosition.setOrderId(ORDER_ID);
        //THEN
        Assert.assertNotNull(orderPosition);
        Assert.assertEquals(expected, orderPosition);
    }

    /*Сценарий: преобразование объекта класса ProductInCartViewDto в объект класса OrderPosition;
    *           объект ProductInCartViewDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenProductInCartEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("productInCartViewDto is marked @NonNull but is null");
        //THEN
        converter.convert(null);
    }
}