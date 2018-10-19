package ua.levelup.dao.impl;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.exception.ApplicationException;
import ua.levelup.testconfig.TestContextConfig;
import ua.levelup.dao.CategoryDao;
import ua.levelup.model.Category;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*Класс CategoryDaoImplTest содержит интеграционные тесты для проверки
* корректности работы слоя доступа к данным, относящимся к сущности
* "Категория товаров"
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class CategoryDaoImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private Properties messagesProperties;

    private Category category;

    /*Сценарий: - добавление в базу данных информации о категории товаров;
    *           - поле parentCategory не равно null.
    *           - родительская категория есть в базе данных
    * Результат: категория товаров успешно добавлена в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void addTest_whenParentCategoryIsNotNullAndExistsInDb_thenOk() throws Exception {
        //GIVEN
        Category parent = new Category("parent category", null);
        parent.setId(1);
        category = new Category("new category", parent);
        //WHEN
        categoryDao.add(category);
        Category extracted = categoryDao.getById(category.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(category, extracted);
    }

    /*Сценарий: - добавление в базу данных информации о категории товаров;
    *           - поле parentCategory не равно null.
    *           - родительской категории нет в базе данных
    * Результат: категория товаров успешно добавлена в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void addTest_whenParentCategoryEqualsNull_thenOk() throws Exception {
        //GIVEN
        category = new Category("new category", null);
        //WHEN
        categoryDao.add(category);
        Category extracted = categoryDao.getById(category.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(category, extracted);
    }

    /*Сценарий: - добавление в базу данных информации о категории товаров;
    *           - поле parentCategory не равно null.
    *           - родительской категории нет в базе данных
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void addTest_whenParentCategoryIsNotNullAndDoesNotExistInDb_thenException()
            throws Exception {
        //GIVEN
        Category parent = new Category();
        parent.setId(1);
        category = new Category("new category", parent);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"));
        //THEN
        categoryDao.add(category);
    }

    /*Сценарий: - добавление в базу данных информации о категории товаров;
    *           - поле name равно null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void addTest_whenCategoryNameEqualsNull_thenException() throws Exception {
        //GIVEN
        category = new Category();
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"));
        //THEN
        categoryDao.add(category);
    }

    /*Сценарий: - добавление в базу данных информации о категории товаров;
    *           - категория с заданным именем уже существует в базе данных.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void addTest_whenCategoryNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        category = new Category("category", null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties.getProperty("NOT_UNIQUE_CATEGORY"));
        //THEN
        categoryDao.add(category);
    }

    /*Сценарий: - добавление в базу данных информации о категории товаров;
    *           - объект вставляемой категории равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void addTest_whenCategoryEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("category is marked @NonNull but is null");
        //THEN
        categoryDao.add(null);
    }

    /*Сценарий: - модификация в базе данных информации о категории товаров;
    *           - категория товаров существует.
    * Результат: операция успешна.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_whenCategoryExists_thenOk() throws Exception {
        //GIVEN
        category = new Category("new category name", null);
        category.setId(2);
        //WHEN
        categoryDao.update(category);
        Category extracted = categoryDao.getById(category.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(category, extracted);
    }

    /*Сценарий: - модификация в базе данных информации о категории товаров;
    *           - категория товаров не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void updateTest_whenCategoryNonexistent_thenOk() throws Exception {
        //GIVEN
        category = new Category("new category name", null);
        category.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_CATEGORY_NONEXISTENT"));
        //WHEN
        categoryDao.update(category);
    }

    /*Сценарий: - модификация в базе данных информации о категории товаров;
    *           - категория товаров существует.
    *           - новое имя категории равно null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_whenNewNameEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"));
        category = new Category();
        category.setId(2);
        //WHEN
        categoryDao.update(category);
    }

    /*Сценарий: - модификация в базе данных информации о категории товаров;
    *           - категория товаров существует.
    *           - новое имя категории является не уникальным
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_whenNewNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties.getProperty("NOT_UNIQUE_CATEGORY"));
        category = new Category("category",null);
        category.setId(1);
        //WHEN
        categoryDao.update(category);
    }

    /*Сценарий: - модификация в базе данных информации о категории товаров;
    *           - категория товаров существует.
    *           - категория после апдейта ссылается на ID несуществующей родительской категории
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void updateTest_whenNewParentCategoryDoesNotExist_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_CATEGORY"));
        Category parent = new Category();
        parent.setId(4);
        category = new Category("new category",parent);
        category.setId(1);
        //WHEN
        categoryDao.update(category);
    }

    /*Сценарий: - изменение в базе данных информации о категории товаров;
    *           - модфифцируемый объект равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void updateTest_whenCategoryEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("category is marked @NonNull but is null");
        //THEN
        categoryDao.update(null);
    }

    /*Сценарий: - удаление из базы данных информации о категории товаров по id категории;
    *           - категория товаров существует.
    *           - категория не имеет связанных с ней дочерних
    * Результат: удаление выполнено успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void deleteTest_whenCategoryExistsAndIsNotParentCategory_thenOk() throws Exception {
        //WHEN
        categoryDao.delete(2);
        //THEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Category.class);
        Category category = categoryDao.getById(2);
    }

    /*Сценарий: - удаление из базы данных информации о категории товаров по id категории;
    *           - категория товаров существует.
    *           - категория имеет связанные с ней дочерние категории
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void deleteTest_whenCategoryIsParentCategory_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_CATEGORY"));
        //THEN
        categoryDao.delete(1);
    }

    /*Сценарий: - удаление из базы данных информации о категории товаров по id категории;
    *           - категория товаров не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void deleteTest_whenCategoryNonexistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_CATEGORY_NONEXISTENT"));
        //THEN
        categoryDao.delete(1);
    }

    /*Сценарий: - получение из базы данных информации о категории товаров по id категории;
    *           - категория товаров существует.
    *           - категория является категорией нулевого уровня (родительская)
    * Результат: операция успешна, метод возвращает объект Category.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getByIdTest_whenCategoryExistsAndIsParentCategory_thenOk() throws Exception {
        //GIVEN
        category = new Category("parent category", null);
        category.setId(1);
        //WHEN
        Category extracted = categoryDao.getById(category.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(category, extracted);
    }

    /*Сценарий: - получение из базы данных информации о категории товаров по id категории;
    *           - категория товаров существует.
    *           - категория является категорией первого уровня (дочерняя)
    * Результат: операция успешна, метод возвращает объект Category.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getByIdTest_whenCategoryExistsAndIsNotParentCategory_thenOk() throws Exception {
        //GIVEN
        Category parent = new Category("parent category", null);
        parent.setId(1);
        category = new Category("category", parent);
        category.setId(2);
        //categoryDao.add(category);
        //WHEN
        Category extracted = categoryDao.getById(category.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(category, extracted);
    }

    /*Сценарий: - получение из базы данных информации о категории товаров по id категории;
    *           - категория товаров с таким ID не существует в базе данных.
    * Результат: ислючение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getByIdTest_whenCategoryNonexistent_thenOk() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Category.class);
        //WHEN
        Category extracted = categoryDao.getById(1);
    }

    /*Сценарий: - удаление из базы данных информации о всех подкатегориях
    *             заданным id родительской категории;
    *           - множество существующих подкатегорий не пустое.
    * Результат: операция успешна, метод возвращает количество удаленных объектов.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void deleteAllByParentIdTest_whenSubcategoriesExist_thenOk() throws Exception {
        //GIVEN
        final int PARENT_ID = 1;
        //WHEN
        List<Category> listBefore = categoryDao.getAllByParentId(PARENT_ID);
        int deletedCount = categoryDao.deleteAllByParentId(PARENT_ID);
        List<Category> listAfter = categoryDao.getAllByParentId(PARENT_ID);
        //THEN
        Assert.assertEquals(listBefore.size(), deletedCount);
        Assert.assertEquals(0, listAfter.size());
    }

    /*Сценарий: - удаление из базы данных информации о всех подкатегориях
    *             заданным id родительской категории;
    *           - множество существующих подкатегорий пустое.
    * Результат: операция успешна, метод возвращает 0.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void deleteAllByParentIdTest_whenSubcategoriesDoNotExist_thenOk() throws Exception {
        //GIVEN
        final int PARENT_ID = 2;
        //WHEN
        List<Category> listBefore = categoryDao.getAllByParentId(PARENT_ID);
        int deletedCount = categoryDao.deleteAllByParentId(PARENT_ID);
        List<Category> listAfter = categoryDao.getAllByParentId(PARENT_ID);
        //THEN
        Assert.assertEquals(0, deletedCount);
        Assert.assertEquals(0, listBefore.size());
        Assert.assertEquals(0, listAfter.size());
    }

    /*Сценарий: - получения из базы данных информации о всех подкатегориях
    *             заданным id родительской категории;
    *           - множество существующих подкатегорий не пустое.
    * Результат: операция успешна, метод возвращает список подкатегорий.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByParentIdTest_whenSubcategoriesExist_thenOk() throws Exception {
        //GIVEN
        List<Category> expected = new ArrayList<>();
        expected.add(categoryDao.getById(2));
        expected.add(categoryDao.getById(3));
        //WHEN
        List<Category> result = categoryDao.getAllByParentId(1);
        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    /*Сценарий: - получения из базы данных информации о всех подкатегориях
    *             заданным id родительской категории;
    *           - множество существующих подкатегорий пустое.
    * Результат: операция успешна, метод возвращает пустой список подкатегорий.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByParentIdTest_whenSubcategoriesDoNotExist_thenOk() throws Exception {
        //WHEN
        List<Category> result = categoryDao.getAllByParentId(2);
        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    /*Сценарий: - получения из базы данных информации о всех категориях
    *             заданного уровня;
    *           - уровень категорий - 0 (родительская категория).
    *           - множество категорий не пустое
    * Результат: операция успешна, метод возвращает список родительских категорий.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByLevelTest_whenParentCategoriesExist_thenOk() throws Exception {
        //GIVEN
        final int LEVEL = 0;
        List<Category> expected = new ArrayList<>();
        expected.add(categoryDao.getById(1));
        //WHEN
        List<Category> result = categoryDao.getAllByLevel(LEVEL);
        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    /*Сценарий: - получения из базы данных информации о всех категориях
    *             заданного уровня;
    *           - уровень категорий - 0 (родительская категория).
    *           - множество категорий пустое
    * Результат: операция успешна, метод возвращает пустой список родительских категорий.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getAllByLevelTest_whenParentCategoriesDoNotExist_thenOk() throws Exception {
        //GIVEN
        final int LEVEL = 0;
        //WHEN
        List<Category> result = categoryDao.getAllByLevel(LEVEL);
        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    /*Сценарий: - получения из базы данных информации о всех категориях
    *             заданного уровня;
    *           - уровень категорий - 1 (дочерняя категория).
    *           - множество категорий не пустое
    * Результат: операция успешна, метод возвращает список дочерних категорий.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_categoryTest.sql"})
    public void getAllByLevelTest_whenLevelOneAndCategoriesExist_thenOk() throws Exception {
        //GIVEN
        final int LEVEL = 1;
        List<Category> expected = new ArrayList<>();
        expected.add(categoryDao.getById(2));
        expected.add(categoryDao.getById(3));
        //WHEN
        List<Category> result = categoryDao.getAllByLevel(LEVEL);
        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(expected, result);
    }

    /*Сценарий: - получения из базы данных информации о всех категориях
    *             заданного уровня;
    *           - уровень категорий - 1 (дочерняя категория).
    *           - множество категорий пустое
    * Результат: операция успешна, метод возвращает пустой список дочерних категорий.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getAllByLevelTest_whenLevelOneAndCategoriesDoNotExist_thenOk() throws Exception {
        //GIVEN
        final int LEVEL = 1;
        //WHEN
        List<Category> result = categoryDao.getAllByLevel(LEVEL);
        //THEN
        Assert.assertNotNull(result);
        Assert.assertEquals(0, result.size());
    }

    /*Сценарий: - получения из базы данных информации о всех категориях
    *             заданного уровня;
    *           - уровень категорий > 1
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getAllByLevelTest_whenLevelIsBiggerThenOne_thenException() throws Exception {
        //GIVEN
        final int LEVEL = 2;
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("UNSUPPORTED_CATEGORY_LEVEL") + LEVEL);
        //THEN
        List<Category> result = categoryDao.getAllByLevel(LEVEL);
    }
}