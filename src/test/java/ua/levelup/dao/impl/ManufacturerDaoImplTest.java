//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.CategoryDao;
//import ua.levelup.dao.ManufacturerDao;
//import ua.levelup.dao.ProductDao;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageHolder;
//import ua.levelup.model.Category;
//import ua.levelup.model.Manufacturer;
//import ua.levelup.model.Product;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import java.util.List;
//
//public class ManufacturerDaoImplTest extends AbstractDaoImplTest {
//    private static final String NAME = "Manufacturer";
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    private ManufacturerDao manufacturerDao;
//    private Manufacturer manufacturer;
//
//    @Before
//    public void init(){
//        manufacturerDao = new ManufacturerDaoImpl(getConnection());
//        manufacturer = new Manufacturer(NAME);
//    }
//
//    @Test
//    public void addAndGetByIdTest() throws Exception {
//        //WHEN
//        Manufacturer returnedManufacturer = manufacturerDao.add(manufacturer);
//        Manufacturer extractedFromDbManufacturer = manufacturerDao.getById(manufacturer.getId());
//        //THEN
//        Assert.assertNotNull(returnedManufacturer);
//        Assert.assertNotNull(extractedFromDbManufacturer);
//        Assert.assertSame(manufacturer, returnedManufacturer);
//        Assert.assertEquals(manufacturer, extractedFromDbManufacturer);
//    }
//
//    @Test
//    public void addTest_whenNotUniqueManufacturer_thenException() throws Exception {
//        //GIVEN
//        manufacturerDao.add(manufacturer);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("FAILED_INSERT_MANUFACTURER"));
//        //WHEN-THEN
//        manufacturerDao.add(manufacturer);
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        //GIVEN
//        manufacturer = manufacturerDao.add(manufacturer);
//        //WHEN
//        int count = manufacturerDao.delete(manufacturer.getId());
//        //THEN
//        Assert.assertEquals(1,count);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("EMPTY_RESULTSET") + Manufacturer.class);
//        Manufacturer extractedFromDb = manufacturerDao.getById(manufacturer.getId());
//    }
//
//    @Test
//    public void updateTest() throws Exception {
//        //GIVEN
//        manufacturer = manufacturerDao.add(manufacturer);
//        manufacturer.setName("New manufacturer");
//        //WHEN
//        int count = manufacturerDao.update(manufacturer);
//        Manufacturer extractedFromDb = manufacturerDao.getById(manufacturer.getId());
//        manufacturer.setId(extractedFromDb.getId());
//        //THEN
//        Assert.assertEquals(1,count);
//        Assert.assertNotNull(extractedFromDb);
//        Assert.assertEquals(manufacturer, extractedFromDb);
//    }
//
//    @Test
//    public void getAllByCategoryIdTest() throws Exception {
//        //GIVEN
//        CategoryDao categoryDao = new CategoryDaoImpl(getConnection());
//        ProductDao productDao = new ProductDaoImpl(getConnection());
//        Category category = new Category("Category",0);
//        manufacturer = manufacturerDao.add(manufacturer);
//        category = categoryDao.add(category);
//        Product product = new Product("Product",1.0f,true,"Description",
//                category.getId(),manufacturer.getId());
//        product = productDao.add(product);
//        //WHEN
//        List<Manufacturer> manufacturers = manufacturerDao.getAllByCategoryId(category.getId());
//        //THEN
//        Assert.assertEquals(1,manufacturers.size());
//        Assert.assertEquals(manufacturer,manufacturers.get(0));
//    }
//
//    @Test
//    public void getAllTest(){
//        //GIVEN
//        manufacturer = manufacturerDao.add(manufacturer);
//        Manufacturer secondManufacturer = manufacturerDao.add(new Manufacturer("other"));
//        //WHEN
//        List<Manufacturer> manufacturers = manufacturerDao.getAll();
//        //THEN
//        Assert.assertEquals(2,manufacturers.size());
//        Assert.assertEquals(manufacturer,manufacturers.get(0));
//        Assert.assertEquals(secondManufacturer,manufacturers.get(1));
//    }
//}