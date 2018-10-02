package ua.levelup.converter;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ua.levelup.model.Category;
import ua.levelup.web.dto.CategoryDto;

/*Класс CategoryConverterTest содержит тесты для проверки
* конвертации объектов класса Category в объекты класса CategoryDto
*
* Автор: Бардась А.А.
* */
public class CategoryConverterTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /*Сценарий: преобразование объекта класса Category в объект класса CategoryDto.
    *           Аргумент метода не равен null
    * Дано:
    *   - Category category
    *   - Category parent
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asCategoryDtoTest() throws Exception {
        //GIVEN
        Category parent = new Category("parent category",null);
        parent.setId(1);
        Category category = new Category("heir category",parent);
        category.setId(2);
        //WHEN
        CategoryDto categoryDto = CategoryConverter.asCategoryDto(category);
        //THEN
        Assert.assertNotNull(categoryDto);
        Assert.assertEquals(category.getId(),categoryDto.getId());
        Assert.assertEquals(category.getName(),categoryDto.getName());
        Assert.assertEquals(category.getParentCategory().getId(),categoryDto.getParentCategoryDto().getId());
    }

    /*Сценарий: преобразование объекта класса Category в объект класса CategoryDto.
    *           Аргумент метода равен null
    * Результат: Преобразование выполнено успешно
    * */
    @Test
    public void asCategoryDtoTest_whenCategoryNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("category is marked @NonNull but is null");
        //THEN
        CategoryConverter.asCategoryDto(null);
    }
}