package ua.levelup.web.dto.view;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Класс CartViewDtoTest содержит юнит-тесты
 * для проверки корректности работы метода putIntoCart() класса CartViewDto
 * <p>
 * Автор: Бардась А.А.
 */
public class CartViewDtoTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CartViewDto cart;
    private ProductInCartViewDto productInCartViewDto;
    private ProductViewDto productViewDto;
    private int count;

    @Before
    public void init() {
        cart = new CartViewDto();
        productViewDto = new ProductViewDto();
        productViewDto.setId(1);
        productViewDto.setName("product");
        productViewDto.setPrice(2.0f);
        productViewDto.setAvailable(true);
        count = 1;
        productInCartViewDto = new ProductInCartViewDto(productViewDto, count);
    }

    /*Сценарий: добавление товара в корзину;
    *           корзина пуста перед добавлением товара.
    * Результат: товар успешно добавлен в корзину.
    * */
    @Test
    public void putIntoCartTest_whenCartWasEmpty_thenOk() throws Exception {
        //WHEN
        cart.putIntoCart(productInCartViewDto);
        //THEN
        Assert.assertEquals(productInCartViewDto, cart.getProductInCartViewDtoList().get(0));
    }

    /*Сценарий: повторное добавление в корзину товара, который уже там находится;
    * Результат: количество товара в корзине корректно увеличивается.
    * */
    @Test
    public void putIntoCartTest_whenProductIsReaddedAndCartIsNotEmpty_thenOk() throws Exception {
        //GIVEN
        cart.putIntoCart(productInCartViewDto);
        count = 2;
        //WHEN
        cart.putIntoCart(productInCartViewDto);
        //THEN
        Assert.assertEquals(count, cart.getProductInCartViewDtoList().get(0).getCount());
    }

    /*Сценарий: Добавление в корзину нового вида товара;
    *           в корзине уже есть товары другого вида.
    * Результат: В корзине появляется новый вид товара.
    * */
    @Test
    public void putIntoCartTest_whenCartWasNotEmpty_thenOk() throws Exception {
        //GIVEN
        cart.putIntoCart(productInCartViewDto);
        ProductViewDto newProduct = new ProductViewDto();
        newProduct.setId(2);
        newProduct.setName("new product");
        newProduct.setPrice(2.5f);
        newProduct.setAvailable(false);
        ProductInCartViewDto newProductInCartViewDto = new ProductInCartViewDto(newProduct, count);
        //WHEN
        cart.putIntoCart(newProductInCartViewDto);
        //THEN
        Assert.assertEquals(productInCartViewDto, cart.getProductInCartViewDtoList().get(0));
        Assert.assertEquals(newProductInCartViewDto, cart.getProductInCartViewDtoList().get(1));
    }

    /*Сценарий: Добавление в корзину нового вида товара;
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void putIntoCartTest_whenProductInCartViewDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("productInCartViewDto is marked @NonNull but is null");
        //THEN
        cart.putIntoCart(null);
    }
}