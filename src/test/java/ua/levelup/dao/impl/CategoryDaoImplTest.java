package ua.levelup.dao.impl;

import ua.levelup.dao.CategoryDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.Category;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

public class CategoryDaoImplTest extends AbstractDaoImplTest {
    private static final String NAME = "Category";
    private static final int PARENT_ID = 0;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    private CategoryDao categoryDao;
    private Category category;

    @Before
    public void init() {
        categoryDao = new CategoryDaoImpl(getConnection());
        category = new Category(NAME, PARENT_ID);
    }

    @Test
    public void addAndGetByIdTest() throws Exception {
        //WHEN
        Category returnedCategory = categoryDao.add(category);
        Category extractedFromDbCategory = categoryDao.getById(returnedCategory.getId());
        //THEN
        Assert.assertNotNull(returnedCategory);
        Assert.assertNotNull(extractedFromDbCategory);
        Assert.assertSame(category, returnedCategory);
        Assert.assertEquals(category, extractedFromDbCategory);
    }

    @Test
    public void addTest_addSubcategory() throws Exception {
        //GIVEN
        category = categoryDao.add(category);
        Category subcategory = new Category("Subcategory", category.getId());
        //WHEN
        Category returnedSubcategory = categoryDao.add(subcategory);
        Category extractedFromDbSubcategory = categoryDao.getById(subcategory.getId());
        subcategory.setId(returnedSubcategory.getId());
        //THEN
        Assert.assertNotNull(returnedSubcategory);
        Assert.assertNotNull(extractedFromDbSubcategory);
        Assert.assertEquals(subcategory, returnedSubcategory);
        Assert.assertEquals(subcategory, extractedFromDbSubcategory);
    }

    @Test
    public void addTest_whenAddSubcategoryInEmptyTable_thenException() throws Exception {
        //GIVEN
        Category subcategory = new Category("Subcategory", 999999);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("FAILED_INSERT_CATEGORY"));
        //WHEN-THEN
        categoryDao.add(subcategory);
    }

    @Test
    public void addTest_whenNotUniqueCategory_thenException() throws Exception {
        //GIVEN
        categoryDao.add(category);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("FAILED_INSERT_CATEGORY"));
        //WHEN-THEN
        categoryDao.add(category);
    }

    @Test
    public void deleteTest() throws Exception {
        //GIVEN
        category = categoryDao.add(category);
        //WHEN
        int count = categoryDao.delete(category.getId());
        //THEN
        Assert.assertEquals(1, count);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("EMPTY_RESULTSET") + Category.class);
        Category extractedFromDb = categoryDao.getById(category.getId());
    }

    @Test
    public void updateTest() throws Exception {
        //GIVEN
        categoryDao.add(category);
        category.setName("New category");
        category.setParentId(0);
        //WHEN
        int count = categoryDao.update(category);
        Category extractedFromDb = categoryDao.getById(category.getId());
        category.setId(extractedFromDb.getId());
        //THEN
        Assert.assertEquals(1, count);
        Assert.assertNotNull(extractedFromDb);
        Assert.assertEquals(category, extractedFromDb);
    }

    @Test
    public void deleteAllSubcategoriesTest() throws Exception {
        //GIVEN
        category = categoryDao.add(category);
        Category firstSubcategory = new Category("FirstSubcategory", category.getId());
        Category secondSubcategory = new Category("SecondSubcategory", category.getId());
        categoryDao.add(firstSubcategory);
        categoryDao.add(secondSubcategory);
        //WHEN
        int count = categoryDao.deleteAllByParentId(category.getId());
        List<Category> subcategories = categoryDao.getAllByParentId(category.getId());
        //THEN
        Assert.assertEquals(2, count);
        Assert.assertEquals(0, subcategories.size());
    }

    @Test
    public void getSubcategoriesTest() throws Exception {
        //GIVEN
        category = categoryDao.add(category);
        Category firstSubcategory = addAndGetCategoryBack(new Category("FirstSubcategory", category.getId()));
        Category secondSubcategory = addAndGetCategoryBack(new Category("SecondSubcategory", category.getId()));
        //WHEN
        List<Category> subcategories = categoryDao.getAllByParentId(category.getId());
        //THEN
        Assert.assertEquals(2, subcategories.size());
        Assert.assertEquals(firstSubcategory, subcategories.get(0));
        Assert.assertEquals(secondSubcategory, subcategories.get(1));
    }

    @Test
    public void getAllByLevelTest_whenLevelZero() throws Exception{
        //GIVEN
        category = categoryDao.add(category);
        Category firstSubcategory = addAndGetCategoryBack(new Category("FirstSubcategory", category.getId()));
        Category secondSubcategory = addAndGetCategoryBack(new Category("SecondSubcategory", category.getId()));
        //WHEN
        List<Category> categoryList = categoryDao.getAllByLevel(0);
        //THEN
        Assert.assertEquals(1,categoryList.size());
        Assert.assertEquals(category,categoryList.get(0));
    }

    @Test
    public void getAllByLevelTest_whenLevelOne() throws Exception{
        //GIVE
        category = categoryDao.add(category);
        Category firstSubcategory = addAndGetCategoryBack(new Category("FirstSubcategory", category.getId()));
        Category secondSubcategory = addAndGetCategoryBack(new Category("SecondSubcategory", category.getId()));
        //WHEN
        List<Category> categoryList = categoryDao.getAllByLevel(1);
        //THEN
        Assert.assertEquals(2,categoryList.size());
        Assert.assertEquals(firstSubcategory,categoryList.get(0));
        Assert.assertEquals(secondSubcategory,categoryList.get(1));
    }

    @Test
    public void getAllByLevelTest_whenLevelBiggerThenOne() throws Exception{
        //GIVEN
        final int LEVEL = 2;
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("UNSUPPORTED_CATEGORY_LEVEL") + LEVEL);
        //WHEN-THEN
        List<Category> categoryList = categoryDao.getAllByLevel(LEVEL);
    }

    private Category addAndGetCategoryBack(Category category){
        categoryDao.add(category);
        return categoryDao.getById(category.getId());
    }
}