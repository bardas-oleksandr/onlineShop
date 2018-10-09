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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.model.*;
import ua.levelup.web.dto.view.CartViewDto;
import ua.levelup.web.dto.view.ProductInCartViewDto;
import ua.levelup.web.dto.view.ProductViewDto;

import java.util.Collections;

/*Класс CartConverterTest содержит тесты для проверки
* конвертации объектов класса Cart в объекты класса CartViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class CartConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Mock
    private ProductInCartConverter productInCartConverter;

    @InjectMocks
    private CartConverter cartConverter;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    /*Сценарий: преобразование объекта класса Cart в объект класса CartViewDto;
    *           объект Cart cart не равен null;
    *           список товаров productInCartList объекта cart содержит одну позицию.
    * Дано:
    *   - Cart cart
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenProductInCartListIsNotEmpty_thenOk() throws Exception {
        //GIVEN
        final int COUNT = 2;
        Cart cart = new Cart();
        ProductInCart productInCart = new ProductInCart(new Product(), COUNT);
        cart.putProduct(productInCart.getProduct(), productInCart.getCount());

        ProductInCartViewDto productInCartViewDto = new ProductInCartViewDto();
        productInCartViewDto.setProductViewDto(new ProductViewDto());
        productInCartViewDto.setCount(COUNT);
        Mockito.when(productInCartConverter.convert(productInCart))
                .thenReturn(productInCartViewDto);

        CartViewDto expected = new CartViewDto();
        expected.setProductInCartViewDtoList(Collections.singletonList(productInCartViewDto));
        //WHEN
        CartViewDto cartViewDto = cartConverter.convert(cart);
        //THEN
        Assert.assertNotNull(cartViewDto);
        Assert.assertEquals(expected, cartViewDto);
        Mockito.verify(productInCartConverter, Mockito.times(1))
                .convert(productInCart);
        Mockito.verifyNoMoreInteractions(productInCartConverter);
    }

    /*Сценарий: преобразование объекта класса Cart в объект класса CartViewDto;
    *           объект Cart cart не равен null;
    *           список товаров productInCartList объекта cart пустой, не равен null.
    * Дано:
    *   - Cart cart
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenProductInCartListIsEmpty_thenOk() throws Exception {
        //GIVEN
        final int COUNT = 2;
        Cart cart = new Cart();
        CartViewDto expected = new CartViewDto();
        //WHEN
        CartViewDto cartViewDto = cartConverter.convert(cart);
        //THEN
        Assert.assertNotNull(cartViewDto);
        Assert.assertEquals(expected, cartViewDto);
        Mockito.verifyNoMoreInteractions(productInCartConverter);
    }

    /*Сценарий: преобразование объекта класса Cart в объект класса CartViewDto;
    *           объект Cart cart равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenCartEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("cart is marked @NonNull but is null");
        //THEN
        cartConverter.convert(null);
    }
}