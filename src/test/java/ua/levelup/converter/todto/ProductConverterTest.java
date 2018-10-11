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
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import ua.levelup.model.Product;
import ua.levelup.web.dto.view.CategoryViewDto;
import ua.levelup.web.dto.view.ManufacturerViewDto;
import ua.levelup.web.dto.view.ProductViewDto;

/*Класс ProductConverterTest содержит тесты для проверки
* конвертации объектов класса Product в объекты класса ProductViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ProductConverterTest {

    @Mock
    CategoryConverter categoryConverter;

    @Mock
    ManufacturerConverter manufacturerConverter;

    @Autowired
    @InjectMocks
    private ProductConverter productConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void init(){
        MockitoAnnotations.initMocks(this);
    }

    /*Сценарий: преобразование объекта класса Product в объект класса ProductViewDto;
    *           объект Product product не равен null.
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenProductNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        Category category = new Category("category", null);
        CategoryViewDto categoryViewDto = new CategoryViewDto();
        categoryViewDto.setName(category.getName());
        Mockito.when(categoryConverter.convert(category)).thenReturn(categoryViewDto);

        Manufacturer manufacturer = new Manufacturer("manufacturer");
        ManufacturerViewDto manufacturerViewDto = new ManufacturerViewDto();
        manufacturerViewDto.setName(manufacturer.getName());
        Mockito.when(manufacturerConverter.convert(manufacturer)).thenReturn(manufacturerViewDto);

        Product product = new Product("productName", 1.1f, true, "description",
                category, manufacturer);
        product.setId(1);

        ProductViewDto expected = new ProductViewDto();
        expected.setId(product.getId());
        expected.setName(product.getName());
        expected.setAvailable(product.isAvailable());
        expected.setPrice(product.getPrice());
        expected.setDescription(product.getDescription());
        expected.setCategoryViewDto(categoryViewDto);
        expected.setManufacturerViewDto(manufacturerViewDto);
        //WHEN
        ProductViewDto productViewDto = productConverter.convert(product);
        //THEN
        Assert.assertNotNull(productViewDto);
        Assert.assertEquals(expected, productViewDto);
    }

    /*Сценарий: преобразование объекта класса Product в объект класса ProductViewDto;
    *           объект Product product равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenProductEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("product is marked @NonNull but is null");
        //THEN
        productConverter.convert(null);
    }
}