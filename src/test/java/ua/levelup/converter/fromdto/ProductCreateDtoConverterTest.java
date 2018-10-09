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
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import ua.levelup.model.Product;
import ua.levelup.web.dto.create.ProductCreateDto;

/*Класс ProductCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса ProductCreateDto в объекты класса Product
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class ProductCreateDtoConverterTest {

    @Autowired
    private ProductCreateDtoConverter productCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса ProductCreateDto в объект класса Product;
    *           объект ProductCreateDto productCreateDto не равен null.
    * Дано:
    *   - ProductCreateDto productCreateDto
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenProductCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        ProductCreateDto dto = new ProductCreateDto("product", 4.0f, true,
                "description", 1, 2);
        Category category = new Category();
        category.setId(dto.getCategoryId());
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(dto.getManufacturerId());
        Product expected = new Product(dto.getName(), dto.getPrice(), dto.isAvailable(),
                dto.getDescription(), category, manufacturer);
        //WHEN
        Product product = productCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(product);
        Assert.assertEquals(expected, product);
    }

    /*Сценарий: преобразование объекта класса ProductCreateDto в объект класса Product;
    *           объект ProductCreateDto productCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenProductCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("productCreateDto is marked @NonNull but is null");
        //THEN
        productCreateDtoConverter.convert(null);
    }
}