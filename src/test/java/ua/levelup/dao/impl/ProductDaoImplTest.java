package ua.levelup.dao.impl;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.dao.CategoryDao;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.dao.ProductDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Category;
import ua.levelup.model.Manufacturer;
import ua.levelup.model.Product;
import ua.levelup.model.SearchParams;
import ua.levelup.testconfig.TestContextConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*Класс ProductDaoImplTest содержит интеграционные тесты для проверки
* корректности работы слоя доступа к данным, относящимся к сущности
* "Товар"
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ProductDaoImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private Properties messagesProperties;

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - все поля товара корректны.
    * Результат: товар успешно добавлен в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenProductIsCorrect_thenOk() throws Exception {
        //GIVEN
        Category category = categoryDao.getById(2);
        Manufacturer manufacturer = manufacturerDao.getById(1);
        Product product = new Product("new product", 2.5f, true
                , "description", category, manufacturer);
        //WHEN
        productDao.add(product);
        //THEN
        Product extracted = productDao.getById(product.getId());
        Assert.assertNotNull(extracted);
        Assert.assertEquals(product, extracted);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - имя товара не уникально.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenProductNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        Category category = categoryDao.getById(2);
        Manufacturer manufacturer = manufacturerDao.getById(1);
        Product product = new Product("first product", 2.5f, true
                , "description", category, manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"));
        //WHEN-THEN
        productDao.add(product);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - имя товара == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenProductNameEqualsNull_thenException() throws Exception {
        //GIVEN
        Category category = categoryDao.getById(2);
        Manufacturer manufacturer = manufacturerDao.getById(1);
        Product product = new Product(null, 2.5f, true
                , "description", category, manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN-THEN
        productDao.add(product);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - цена товара < 0.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenPriceIsNegative_thenException() throws Exception {
        //GIVEN
        Category category = categoryDao.getById(2);
        Manufacturer manufacturer = manufacturerDao.getById(1);
        Product product = new Product("new product", -0.1f, true
                , "description", category, manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN-THEN
        productDao.add(product);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - категории с заданным ID не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenCategoryNonexistent_thenException() throws Exception {
        //GIVEN
        Category category = new Category();
        category.setId(10);
        Manufacturer manufacturer = manufacturerDao.getById(1);
        Product product = new Product("new product", 1.0f, true
                , "description", category, manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN-THEN
        productDao.add(product);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - поле category == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenCategoryEqualsNull_thenException() throws Exception {
        //GIVEN
        Manufacturer manufacturer = manufacturerDao.getById(1);
        Product product = new Product("new product", 1.0f, true
                , "description", null, manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN-THEN
        productDao.add(product);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - производителя с заданным ID не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenManufacturerNonexistent_thenException() throws Exception {
        //GIVEN
        Category category = categoryDao.getById(2);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(10);
        Product product = new Product("new product", 1.0f, true
                , "description", category, manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN-THEN
        productDao.add(product);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - поле manufacturer == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenManufacturerEqualsNull_thenException() throws Exception {
        //GIVEN
        Category category = categoryDao.getById(2);
        Product product = new Product("new product", 1.0f, true
                , "description", category, null);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN-THEN
        productDao.add(product);
    }

    /*Сценарий: - добавление в базу данных информации о товаре;
    *           - product == null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void addTest_whenProductEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("product is marked @NonNull but is null");
        //WHEN-THEN
        productDao.add(null);
    }

    /*Сценарий: - модификация в базе данных информации о товаре;
    *           - все поля товара корректны.
    * Результат: товар успешно изменен.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_whenProductIsCorrect_thenOk() throws Exception {
        //GIVEN
        Product product = productDao.getById(1);
        product.setName("new name");
        product.setDescription("new description");
        product.setAvailable(false);
        product.setPrice(99.99f);
        Category category = categoryDao.getById(3);
        product.setCategory(category);
        Manufacturer manufacturer = manufacturerDao.getById(3);
        product.setManufacturer(manufacturer);
        //WHEN
        productDao.update(product);
        //THEN
        Product extracted = productDao.getById(product.getId());
        Assert.assertNotNull(extracted);
        Assert.assertEquals(product, extracted);
    }

    /*Сценарий: - модификация в базе данных информации о товаре;
    *           - имя товара не уникально.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_whenNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        Product product = productDao.getById(1);
        product.setName("second product");
        product.setDescription("new description");
        product.setAvailable(false);
        product.setPrice(99.99f);
        Category category = categoryDao.getById(3);
        product.setCategory(category);
        Manufacturer manufacturer = manufacturerDao.getById(3);
        product.setManufacturer(manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_PRODUCT"));
        //WHEN
        productDao.update(product);
    }

    /*Сценарий: - модификация в базе данных информации о товаре;
    *           - имя товара == null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_whenNameEqualsNull_thenException() throws Exception {
        //GIVEN
        Product product = productDao.getById(1);
        product.setName(null);
        product.setDescription("new description");
        product.setAvailable(false);
        product.setPrice(99.99f);
        Category category = categoryDao.getById(3);
        product.setCategory(category);
        Manufacturer manufacturer = manufacturerDao.getById(3);
        product.setManufacturer(manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN
        productDao.update(product);
    }

    /*Сценарий: - модификация в базе данных информации о товаре;
    *           - цена товара меньше нуля.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_whenPriceIsNegative_thenException() throws Exception {
        //GIVEN
        Product product = productDao.getById(1);
        product.setName("new name");
        product.setDescription("new description");
        product.setAvailable(false);
        product.setPrice(-0.1f);
        Category category = categoryDao.getById(3);
        product.setCategory(category);
        Manufacturer manufacturer = manufacturerDao.getById(3);
        product.setManufacturer(manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN
        productDao.update(product);
    }

    /*Сценарий: - модификация в базе данных информации о товаре;
    *           - категории товаров с заданным ID не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_whenCategoryNonexistent_thenException() throws Exception {
        //GIVEN
        Product product = productDao.getById(1);
        product.setName("new name");
        product.setDescription("new description");
        product.setAvailable(false);
        product.setPrice(99.9f);
        Category category = new Category();
        category.setId(10);
        product.setCategory(category);
        Manufacturer manufacturer = manufacturerDao.getById(3);
        product.setManufacturer(manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN
        productDao.update(product);
    }

    /*Сценарий: - модификация в базе данных информации о товаре;
    *           - производителя товаров с заданным ID не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_whenManufacturerNonexistent_thenException() throws Exception {
        //GIVEN
        Product product = productDao.getById(1);
        product.setName("new name");
        product.setDescription("new description");
        product.setAvailable(false);
        product.setPrice(99.9f);
        Category category = categoryDao.getById(3);
        product.setCategory(category);
        Manufacturer manufacturer = new Manufacturer();
        manufacturer.setId(10);
        product.setManufacturer(manufacturer);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_PRODUCT"));
        //WHEN
        productDao.update(product);
    }

    /*Сценарий: - модификация в базе данных информации о товаре;
    *           - product == null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void updateTest_whenProductEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("product is marked @NonNull but is null");
        //WHEN-THEN
        productDao.update(null);
    }

    /*Сценарий: - удаление из базы данных информации о товаре;
    *           - товар существует.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void deleteTest_whenProductExist_thenOk() throws Exception {
        //GIVEN
        Product product = productDao.getById(1);
        //WHEN
        productDao.delete(product.getId());
        //THEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Product.class);
        productDao.getById(product.getId());
    }

    /*Сценарий: - удаление из базы данных информации о товаре;
    *           - товар не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void deleteTest_whenProductNonexistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_PRODUCT_NONEXISTENT"));
        //WHEN-THEN
        productDao.delete(10);
    }

    /*Сценарий: - получение из базы данных информации о товаре;
    *           - товар существует.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getByIdTest_whenProductExists_thenOk() throws Exception {
        //GIVEN
        Category category = categoryDao.getById(2);
        Manufacturer manufacturer = manufacturerDao.getById(1);
        Product expected = new Product("first product", 2.0f, true
                , "description", category, manufacturer);
        expected.setId(1);
        //WHEN
        Product product = productDao.getById(1);
        //THEN
        Assert.assertNotNull(product);
        Assert.assertEquals(expected, product);
    }

    /*Сценарий: - получение из базы данных информации о товаре;
    *           - товар не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getByIdTest_whenProductNonxistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Product.class);
        //WHEN-THEN
        Product product = productDao.getById(10);
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenPriceBeetween_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(2.5f);
        searchParams.setMaxPrice(4.5f);
        searchParams.setOrderMethod(SearchParams.OrderMethod.CHEAP_FIRST);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(2));
        expected.add(productDao.getById(3));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected.size(), productList.size());
        Assert.assertTrue(expected.contains(productList.get(0)));
        Assert.assertTrue(expected.contains(productList.get(1)));
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен, подкатегория товаров.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenSelectBySubcategory_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(0.5f);
        searchParams.setMaxPrice(99.9f);
        searchParams.setCategoryId(2);
        searchParams.setOrderMethod(SearchParams.OrderMethod.CHEAP_FIRST);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(1));
        expected.add(productDao.getById(2));
        expected.add(productDao.getById(3));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected.size(), productList.size());
        Assert.assertTrue(expected.contains(productList.get(0)));
        Assert.assertTrue(expected.contains(productList.get(1)));
        Assert.assertTrue(expected.contains(productList.get(2)));
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен, категория товаров (родительская категория).
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenSelectByCategory_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(0.5f);
        searchParams.setMaxPrice(99.9f);
        searchParams.setCategoryId(1);
        searchParams.setOrderMethod(SearchParams.OrderMethod.CHEAP_FIRST);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(1));
        expected.add(productDao.getById(2));
        expected.add(productDao.getById(3));
        expected.add(productDao.getById(4));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected.size(), productList.size());
        Assert.assertTrue(expected.contains(productList.get(0)));
        Assert.assertTrue(expected.contains(productList.get(1)));
        Assert.assertTrue(expected.contains(productList.get(2)));
        Assert.assertTrue(expected.contains(productList.get(3)));
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен, производитель товаров.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenSelectByManufacturer_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(0.5f);
        searchParams.setMaxPrice(99.9f);
        searchParams.setManufacturerId(1);
        searchParams.setOrderMethod(SearchParams.OrderMethod.CHEAP_FIRST);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(1));
        expected.add(productDao.getById(2));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected.size(), productList.size());
        Assert.assertTrue(expected.contains(productList.get(0)));
        Assert.assertTrue(expected.contains(productList.get(1)));
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен, только доступные.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenSelectOnlyAvailable_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(0.5f);
        searchParams.setMaxPrice(99.9f);
        searchParams.setAvailableOnly(true);
        searchParams.setOrderMethod(SearchParams.OrderMethod.CHEAP_FIRST);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(1));
        expected.add(productDao.getById(3));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected.size(), productList.size());
        Assert.assertTrue(expected.contains(productList.get(0)));
        Assert.assertTrue(expected.contains(productList.get(1)));
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен, сортировка по названию.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenSortByName_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(0.5f);
        searchParams.setMaxPrice(99.9f);
        searchParams.setOrderMethod(SearchParams.OrderMethod.PRODUCT_NAME);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(1));
        expected.add(productDao.getById(4));
        expected.add(productDao.getById(2));
        expected.add(productDao.getById(3));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected, productList);
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен, сортировка по возрастанию цены.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenCheapFirst_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(0.5f);
        searchParams.setMaxPrice(99.9f);
        searchParams.setOrderMethod(SearchParams.OrderMethod.CHEAP_FIRST);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(1));
        expected.add(productDao.getById(2));
        expected.add(productDao.getById(3));
        expected.add(productDao.getById(4));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected, productList);
    }

    /*Сценарий: - получение из базы данных фильтрованного списка товаров;
    *           - параметры фильтра: диапазон цен, сортировка по убыванию цены.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_productTest.sql"})
    public void getFilteredProductsTest_whenCheapLast_thenOk() throws Exception {
        //GIVEN
        SearchParams searchParams = new SearchParams();
        searchParams.setMinPrice(0.5f);
        searchParams.setMaxPrice(99.9f);
        searchParams.setOrderMethod(SearchParams.OrderMethod.CHEAP_LAST);
        List<Product> expected = new ArrayList<>();
        expected.add(productDao.getById(4));
        expected.add(productDao.getById(3));
        expected.add(productDao.getById(2));
        expected.add(productDao.getById(1));
        //WHEN
        List<Product> productList = productDao.getFilteredProducts(searchParams);
        //THEN
        Assert.assertNotNull(productList);
        Assert.assertEquals(expected, productList);
    }
}