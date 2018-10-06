package ua.levelup.converter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.levelup.converter.toViewDto.ProductConverter;
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import ua.levelup.model.Product;
import ua.levelup.web.dto.view.ProductViewDto;

/*Класс ProductConverterTest содержит тесты для проверки
* конвертации объектов класса Product в объекты класса ProductDto
*
* Автор: Бардась А.А.
* */
public class ProductConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса Product в объект класса ProductDto.
    *           Аргумент метода не равен null
    * Дано:
    *   - Product product
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asProductDtoTest() throws Exception {
        //GIVEN
        Category category = new Category("category", null);
        Manufacturer manufacturer = new Manufacturer("manufacturer");
        Product product = new Product("productName", 1.1f, true, "description",
                category, manufacturer);
        product.setId(1);
        //WHEN
        ProductViewDto productDto = ProductConverter.asProductViewDto(product);
        //THEN
        Assert.assertNotNull(productDto);
        Assert.assertEquals(product.getId(), productDto.getId());
        Assert.assertEquals(product.getName(), productDto.getName());
        Assert.assertEquals(product.getPrice(), productDto.getPrice(), 0);
        Assert.assertEquals(product.isAvailable(), productDto.isAvailable());
        Assert.assertEquals(product.getDescription(), productDto.getDescription());
        Assert.assertEquals(product.getCategory().getName(), productDto.getCategoryDto().getName());
        Assert.assertEquals(product.getManufacturer().getName(), productDto.getManufacturerDto().getName());
    }

    /*Сценарий: преобразование объекта класса Product в объект класса ProductDto.
    *           Аргумент метода равен null
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asProductDtoTest_whenProductNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("product is marked @NonNull but is null");
        //THEN
        ProductConverter.asProductViewDto(null);
    }
}