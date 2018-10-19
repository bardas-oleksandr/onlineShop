package ua.levelup.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.dao.ManufacturerDao;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.Manufacturer;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import ua.levelup.testconfig.TestContextConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*Класс ManufacturerDaoImplTest содержит интеграционные тесты для проверки
* корректности работы слоя доступа к данным, относящимся к сущности
* "Производитель товаров"
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class ManufacturerDaoImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private ManufacturerDao manufacturerDao;

    @Autowired
    private Properties messagesProperties;

    private Manufacturer manufacturer;

    /*Сценарий: - добавление в базу данных информации о производителе товаров;
    *           - поле name не равно null и является уникальным.
    * Результат: производитель товаров успешно добавлен в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void addTest_whenNameIsUnique_thenOk() throws Exception {
        //GIVEN
        manufacturer = new Manufacturer("manufacturer");
        //WHEN
        manufacturerDao.add(manufacturer);
        Manufacturer extracted = manufacturerDao.getById(manufacturer.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(manufacturer, extracted);
    }

    /*Сценарий: - добавление в базу данных информации о производителе товаров;
    *           - поле name равно null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void addTest_whenNameEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_MANUFACTURER"));
        //WHEN-THEN
        manufacturerDao.add(new Manufacturer());
    }

    /*Сценарий: - добавление в базу данных информации о производителе товаров;
    *           - поле name не уникально.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void addTest_whenNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        Manufacturer extracted = manufacturerDao.getById(1);
        manufacturer = new Manufacturer("first manufacturer");
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_MANUFACTURER"));
        //WHEN-THEN
        manufacturerDao.add(manufacturer);
    }

    /*Сценарий: - добавление в базу данных информации о производителе товаров;
    *           - производитель товаров равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void addTest_whenManufacturerEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("manufacturer is marked @NonNull but is null");
        //WHEN-THEN
        manufacturerDao.add(null);
    }

    /*Сценарий: - модификация в базе данных информации о производителе товаров;
    *           - новое имя производителя уникально.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_whenNewNameIsUnique_thenOk() throws Exception {
        //GIVEN
        manufacturer = new Manufacturer("manufacturer");
        manufacturer.setId(1);
        //WHEN
        manufacturerDao.update(manufacturer);
        Manufacturer extracted = manufacturerDao.getById(manufacturer.getId());
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(manufacturer, extracted);
    }

    /*Сценарий: - модификация в базе данных информации о производителе товаров;
    *           - новое имя производителя не является уникально.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_whenNewNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        manufacturer = new Manufacturer("second manufacturer");
        manufacturer.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_MANUFACTURER"));
        //WHEN-THEN
        manufacturerDao.update(manufacturer);
    }

    /*Сценарий: - модификация в базе данных информации о производителе товаров;
    *           - новое имя производителя равно null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_whenNewNameEqualsNull_thenException() throws Exception {
        //GIVEN
        manufacturer = new Manufacturer();
        manufacturer.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("DATA_INTEGRITY_VIOLATION_FOR_MANUFACTURER"));
        //WHEN-THEN
        manufacturerDao.update(manufacturer);
    }

    /*Сценарий: - модификация в базе данных информации о производителе товаров;
    *           - производитель с заданным id не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_whenManufacturerNonexistent_thenOk() throws Exception {
        //GIVEN
        manufacturer = new Manufacturer("manufacturer");
        manufacturer.setId(5);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_MANUFACTURER_NONEXISTENT"));
        //WHEN-THEN
        manufacturerDao.update(manufacturer);
    }

    /*Сценарий: - модификация в базе данных информации о производителе товаров;
    *           - производитель равен null.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void updateTest_whenManufacturerEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("manufacturer is marked @NonNull but is null");
        //WHEN-THEN
        manufacturerDao.update(null);
    }

    /*Сценарий: - удаление из базы данных информации о производителе товаров;
    *           - производитель с указанным ID существует;
    *           - производитель не связан ни с одним товаром.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void deleteTest_whenManufacturerExistsAndHasNoProducts_thenOk() throws Exception {
        //GIVEN
        final int ID = 4;
        //WHEN
        manufacturerDao.delete(ID);
        //THEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Manufacturer.class);
        manufacturerDao.getById(ID);
    }

    /*Сценарий: - удаление из базы данных информации о производителе товаров;
    *           - производитель с указанным ID существует;
    *           - производитель связан хотя бы с одним товаром.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void deleteTest_whenManufacturerExistsAndHasProducts_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("INTEGRITY_VIOLATION_WHILE_DELETE_MANUFACTURER"));
        //WHEN-THEN
        manufacturerDao.delete(1);
    }

    /*Сценарий: - удаление из базы данных информации о производителе товаров;
    *           - производитель с указанным ID не существует;
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void deleteTest_whenManufacturerNonexistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_UPDATE_MANUFACTURER_NONEXISTENT"));
        //WHEN-THEN
        manufacturerDao.delete(5);
    }

    /*Сценарий: - получение из базы данных информации о производителе товаров;
    *           - производитель с указанным ID существует;
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void getByIdTest_whenManufacturerExists_thenOk() throws Exception {
        //GIVEN
        Manufacturer expectged = new Manufacturer("first manufacturer");
        expectged.setId(1);
        //WHEN
        manufacturer = manufacturerDao.getById(1);
        //THEN
        Assert.assertNotNull(manufacturer);
        Assert.assertEquals(expectged, manufacturer);
    }

    /*Сценарий: - получение из базы данных информации о производителе товаров;
    *           - производитель с указанным ID не существует;
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getByIdTest_whenManufacturerNonexistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + Manufacturer.class);
        //WHEN-THEN
        manufacturer = manufacturerDao.getById(1);
    }

    /*Сценарий: - получение из базы данных информации о всех производителях товаров заданной категории;
    *           - категория товаров имеет связанные товары;
    *           - категория товаров не является родительской
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void getAllByCategoryIdTest_whenCategoryIsNotParentCategory_thenOk() throws Exception {
        //GIVEN
        List<Manufacturer> expected = new ArrayList<>();
        expected.add(manufacturerDao.getById(1));
        expected.add(manufacturerDao.getById(2));
        //WHEN
        List<Manufacturer> manufacturerList = manufacturerDao.getAllByCategoryId(2);
        //THEN
        Assert.assertNotNull(manufacturerList);
        Assert.assertEquals(expected, manufacturerList);
    }

    /*Сценарий: - получение из базы данных информации о всех производителях товаров заданной категории;
    *           - категория товаров имеет связанные товары;
    *           - категория товаров является родительской
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void getAllByCategoryIdTest_whenCategoryIsParentCategory_thenOk() throws Exception {
        //GIVEN
        List<Manufacturer> expected = new ArrayList<>();
        expected.add(manufacturerDao.getById(1));
        expected.add(manufacturerDao.getById(2));
        expected.add(manufacturerDao.getById(3));
        //WHEN
        List<Manufacturer> manufacturerList = manufacturerDao.getAllByCategoryId(1);
        //THEN
        Assert.assertNotNull(manufacturerList);
        Assert.assertEquals(expected, manufacturerList);
    }

    /*Сценарий: - получение из базы данных информации о всех производителях товаров заданной категории;
    *           - категория товаров не имеет связанных товаров;
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void getAllByCategoryIdTest_whenCategoryHasNoProducts_thenOk() throws Exception {
        //WHEN
        List<Manufacturer> manufacturerList = manufacturerDao.getAllByCategoryId(3);
        //THEN
        Assert.assertNotNull(manufacturerList);
        Assert.assertEquals(0, manufacturerList.size());
    }

    /*Сценарий: - получение из базы данных информации о всех производителях товаров заданной категории;
    *           - категория товаров c указанным ID не существует;
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void getAllByCategoryIdTest_whenCategoryNonexistent_thenOk() throws Exception {
        //WHEN
        List<Manufacturer> manufacturerList = manufacturerDao.getAllByCategoryId(10);
        //THEN
        Assert.assertNotNull(manufacturerList);
        Assert.assertEquals(0, manufacturerList.size());
    }

    /*Сценарий: - получение из базы данных информации о всех производителях товаров;
    *           - в базе данных есть производители;
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_manufacturerTest.sql"})
    public void getAllTest_whenManufacturersExist_thenOk() throws Exception {
        //GIVEN
        List<Manufacturer> expected = new ArrayList<>();
        expected.add(manufacturerDao.getById(1));
        expected.add(manufacturerDao.getById(2));
        expected.add(manufacturerDao.getById(3));
        expected.add(manufacturerDao.getById(4));
        //WHEN
        List<Manufacturer> manufacturerList = manufacturerDao.getAll();
        //THEN
        Assert.assertNotNull(manufacturerList);
        Assert.assertEquals(expected.size(), manufacturerList.size());
        Assert.assertTrue(manufacturerList.contains(expected.get(0)));
        Assert.assertTrue(manufacturerList.contains(expected.get(1)));
        Assert.assertTrue(manufacturerList.contains(expected.get(2)));
        Assert.assertTrue(manufacturerList.contains(expected.get(3)));
    }

    /*Сценарий: - получение из базы данных информации о всех производителях товаров;
    *           - в базе данных нет производителей;
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getAllTest_whenManufacturersDoNotExist_thenOk() throws Exception {
        //WHEN
        List<Manufacturer> manufacturerList = manufacturerDao.getAllByCategoryId(10);
        //THEN
        Assert.assertNotNull(manufacturerList);
        Assert.assertEquals(0, manufacturerList.size());
    }
}