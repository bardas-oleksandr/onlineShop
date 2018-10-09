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
import ua.levelup.web.dto.create.CategoryCreateDto;

/*Класс CategoryCreateDtoConverterTest содержит тесты для проверки
* конвертации объектов класса CategoryCreateDto в объекты класса Category
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class CategoryCreateDtoConverterTest {

    @Autowired
    private CategoryCreateDtoConverter categoryCreateDtoConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса CategoryCreateDto в объект класса Category;
    *           объект CategoryCreateDto categoryCreateDto не равен null.
    * Дано:
    *   - CategoryCreateDto categoryCreateDto
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenCategoryCreateDtoNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        CategoryCreateDto dto = new CategoryCreateDto("category", 1);
        Category parent = new Category(null, null);
        parent.setId(dto.getParentCategoryId());
        Category expected = new Category(dto.getName(), parent);
        //WHEN
        Category category = categoryCreateDtoConverter.convert(dto);
        //THEN
        Assert.assertNotNull(category);
        Assert.assertEquals(expected, category);
    }

    /*Сценарий: преобразование объекта класса CategoryCreateDto в объект класса Category;
    *           объект CategoryCreateDto сategoryCreateDto равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenCategoryCreateDtoEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("categoryCreateDto is marked @NonNull but is null");
        //THEN
        categoryCreateDtoConverter.convert(null);
    }
}