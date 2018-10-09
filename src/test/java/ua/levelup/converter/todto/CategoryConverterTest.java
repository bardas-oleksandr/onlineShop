package ua.levelup.converter.todto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.config.TestContextConfig;
import ua.levelup.model.Category;
import ua.levelup.web.dto.view.CategoryViewDto;

/*Класс CategoryConverterTest содержит тесты для проверки
* конвертации объектов класса Category в объекты класса CategoryViewDto
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
public class CategoryConverterTest {

    @Autowired
    private CategoryConverter categoryConverter;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private Category category;

    @Before
    public void init(){
        Category parent = new Category("parent category",null);
        parent.setId(1);
        category = new Category("heir category", parent);
        category.setId(2);
    }

    /*Сценарий: преобразование объекта класса Category в объект класса CategoryViewDto;
    *           объект Category category не равен null;
    *           поле Category parentCategory объекта Category category не равно null.
    * Дано:
    *   - Category category
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenParentCategoryNotEqualsNull_thenOk() throws Exception {
        //GIVEN
        CategoryViewDto expected = new CategoryViewDto();
        expected.setId(category.getId());
        expected.setName(category.getName());
        CategoryViewDto expectedParent = new CategoryViewDto();
        expectedParent.setId(category.getParentCategory().getId());
        expectedParent.setName(category.getParentCategory().getName());
        expected.setParentCategoryViewDto(expectedParent);
        //WHEN
        CategoryViewDto categoryViewDto = categoryConverter.convert(category);
        //THEN
        Assert.assertNotNull(categoryViewDto);
        Assert.assertEquals(expected, categoryViewDto);
    }

    /*Сценарий: преобразование объекта класса Category в объект класса CategoryViewDto;
    *           объект Category category не равен null;
    *           поле String name объекта Category category равно null.
    * Дано:
    *   - Category category
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenCategoryNameEqualsNull_thenOk() throws Exception {
        //GIVEN
        category.setName(null);
        CategoryViewDto expected = new CategoryViewDto();
        expected.setId(category.getId());
        CategoryViewDto expectedParent = new CategoryViewDto();
        expectedParent.setId(category.getParentCategory().getId());
        expectedParent.setName(category.getParentCategory().getName());
        expected.setParentCategoryViewDto(expectedParent);
        //WHEN
        CategoryViewDto categoryViewDto = categoryConverter.convert(category);
        //THEN
        Assert.assertNotNull(categoryViewDto);
        Assert.assertEquals(expected, categoryViewDto);
    }

    /*Сценарий: преобразование объекта класса Category в объект класса CategoryViewDto;
    *           объект Category category не равен null;
    *           поле Category parentCategory объекта Category category равно null.
    * Дано:
    *   - Category category
    * Результат: преобразование выполнено успешно.
    * */
    @Test
    public void convertTest_whenParentCategoryEqualsNull_thenOk() throws Exception {
        //GIVEN
        category.setParentCategory(null);
        CategoryViewDto expected = new CategoryViewDto();
        expected.setId(category.getId());
        expected.setName(category.getName());
        //WHEN
        CategoryViewDto categoryViewDto = categoryConverter.convert(category);
        //THEN
        Assert.assertNotNull(categoryViewDto);
        Assert.assertEquals(expected, categoryViewDto);
    }

    /*Сценарий: преобразование объекта класса Category в объект класса CategoryViewDto;
    *           объект Category category равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    public void convertTest_whenCategoryEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("category is marked @NonNull but is null");
        //THEN
        categoryConverter.convert(null);
    }
}