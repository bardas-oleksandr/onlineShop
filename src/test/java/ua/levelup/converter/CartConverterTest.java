package ua.levelup.converter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.levelup.converter.toViewDto.CartConverter;
import ua.levelup.model.Cart;
import ua.levelup.web.dto.view.CartViewDto;

import java.util.LinkedHashMap;

/*Класс CartConverterTest содержит тесты для проверки
* конвертации объектов класса Cart в объекты класса CartDto
*
* Автор: Бардась А.А.
* */
public class CartConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса Cart в объект класса CartDto.
    *           Аргумент метода не равен null
    * Дано:
    *   - Cart cart
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asCartDtoTest() throws Exception {
        //GIVEN
        Cart cart = new Cart();
        CartViewDto expected = new CartViewDto();
        expected.setProductCountMap(new LinkedHashMap<>());
        expected.setProductDtoMap(new LinkedHashMap<>());
        expected.setSize(0);
        //WHEN
        CartViewDto cartDto = CartConverter.asCartViewDto(cart);
        //THEN
        Assert.assertNotNull(cartDto);
        Assert.assertEquals(expected, cartDto);
    }

    /*Сценарий: преобразование объекта класса Cart в объект класса CartDto.
    *           Аргумент метода равен null
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asCartDtoTest_whenCartNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("cart is marked @NonNull but is null");
        //THEN
        CartConverter.asCartViewDto(null);
    }
}