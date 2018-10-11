package ua.levelup.converter.todto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.model.Product;
import ua.levelup.model.ProductInCart;
import ua.levelup.web.dto.view.ProductInCartViewDto;
import ua.levelup.web.dto.view.ProductViewDto;

/*Класс ProductInCartConverterTest содержит тесты для проверки
* конвертации объектов класса ProductInCart в объекты класса ProductInCartViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ProductInCartConverterTest {

    @Mock
    ProductConverter productConverter;

    @Autowired
    @InjectMocks
    private ProductInCartConverter productInCartConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    /*Сценарий: преобразование объекта класса ProductInCart в объект класса ProductInCartViewDto;
    *           объект ProductInCart productInCart не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenProductInCartNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        Product product = new Product();
        product.setId(1);
        ProductViewDto productViewDto = new ProductViewDto();
        productViewDto.setId(product.getId());
        Mockito.when(productConverter.convert(product)).thenReturn(productViewDto);

        ProductInCart productInCart = new ProductInCart(product,2);
        ProductInCartViewDto expected = new ProductInCartViewDto();
        expected.setCount(productInCart.getCount());
        expected.setProductViewDto(productViewDto);
        //WHEN
        ProductInCartViewDto productInCartViewDto = productInCartConverter.convert(productInCart);
        //THEN
        Assert.assertNotNull(productViewDto);
        Assert.assertEquals(expected, productInCartViewDto);
        Mockito.verify(productConverter, Mockito.times(1)).convert(product);
        Mockito.verifyNoMoreInteractions(productConverter);
    }

    /*Сценарий: преобразование объекта класса ProductInCart в объект класса ProductInCartViewDto;
    *           объект ProductInCart productInCart равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenProductInCartEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("productInCart is marked @NonNull but is null");
        //THEN
        productInCartConverter.convert(null);
    }
}