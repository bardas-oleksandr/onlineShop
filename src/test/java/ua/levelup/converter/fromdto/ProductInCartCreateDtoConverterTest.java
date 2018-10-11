package ua.levelup.converter.fromdto;

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
import ua.levelup.model.Product;
import ua.levelup.model.ProductInCart;
import ua.levelup.web.dto.create.ProductInCartCreateDto;

/*Класс ProductInCartCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса ProductInCartCreateDto в объекты класса ProductInCart
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ProductInCartCreateDtoConverterTest {

    @Autowired
    private ProductInCartCreateDtoConverter productInCartCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса ProductInCartCreateDto в объект класса ProductInCart;
    *           объект ProductInCartCreateDto productInCartCreateDto не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenProductInCartCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        ProductInCartCreateDto dto = new ProductInCartCreateDto(1, 2);
        Product product = new Product();
        product.setId(dto.getProductId());
        ProductInCart expected = new ProductInCart(product, dto.getCount());
        //WHEN
        ProductInCart productInCart = productInCartCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(productInCart);
        Assert.assertEquals(expected, productInCart);
    }

    /*Сценарий: преобразование объекта класса ProductInCartCreateDto в объект класса ProductInCart;
    *           объект ProductInCartCreateDto productInCartCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenProductInCartCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("productInCartCreateDto is marked @NonNull but is null");
        //THEN
        productInCartCreateDtoConverter.convert(null);
    }
}