package ua.levelup.converter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.levelup.model.Cart;
import ua.levelup.web.dto.CartDto;

import java.util.LinkedHashMap;

import static org.junit.Assert.*;

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
        CartDto expected = new CartDto();
        expected.setProductCountMap(new LinkedHashMap<>());
        expected.setProductDtoMap(new LinkedHashMap<>());
        expected.setSize(0);
        //WHEN
        CartDto cartDto = CartConverter.asCartDto(cart);
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
        CartConverter.asCartDto(null);
    }
}