//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.CategoryDao;
//import ua.levelup.dao.ManufacturerDao;
//import ua.levelup.dao.ProductDao;
//import ua.levelup.dao.support.OrderMethod;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageHolder;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//import ua.levelup.model.Category;
//import ua.levelup.model.Manufacturer;
//import ua.levelup.model.Product;
//
//import java.util.List;
//
//public class ProductDaoImplTest extends AbstractDaoImplTest {
//    private static final String NAME = "John";
//    private static final String PASSWORD = "password";
//    private static final String EMAIL = "john@gmail.com";
//    private static final int STATE = 0;
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    private ProductDao productDao;
//    private Product product;
//
//    @Before
//    public void init() {
//        ManufacturerDao manufacturerDao = new ManufacturerDaoImpl(getConnection());
//        CategoryDao categoryDao = new CategoryDaoImpl(getConnection());
//        productDao = new ProductDaoImpl(getConnection());
//        Manufacturer manufacturer = manufacturerDao.add(new Manufacturer("manufacturer"));
//        Category category = categoryDao.add(new Category("category", 0));
//        product = new Product("first product", 1.0f, true,
//                "first description", category.getId(), manufacturer.getId());
//        product.setCategoryName(category.getName());
//        product.setManufacturerName(manufacturer.getName());
//    }
//
//    @Test
//    public void addAndGetTest() throws Exception {
//        //WHEN
//        Product returnedProduct = productDao.add(product);
//        Product extractedFromDbProduct = productDao.getById(product.getId());
//        //THEN
//        Assert.assertNotNull(returnedProduct);
//        Assert.assertNotNull(extractedFromDbProduct);
//        Assert.assertSame(product, returnedProduct);
//        Assert.assertEquals(product, extractedFromDbProduct);
//    }
//
//    @Test
//    public void addTest_whenNonexistentCategory_thenException() throws Exception {
//        //GIVEN
//        product = new Product("second product", 1.0f, true,
//                "second description", product.getCategoryId() + 999,
//                product.getManufacturerId());
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("FAILED_INSERT_PRODUCT"));
//        //WHEN-THEN
//        productDao.add(product);
//    }
//
//    @Test
//    public void addTest_whenNonexistentManufacturer_thenException() throws Exception {
//        //GIVEN
//        product = new Product("second product", 1.0f, true,
//                "second description", product.getCategoryId(),
//                product.getManufacturerId() + 999);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("FAILED_INSERT_PRODUCT"));
//        //WHEN-THEN
//        productDao.add(product);
//    }
//
//    @Test
//    public void addTest_whenNotUniqueProduct_thenException() throws Exception {
//        //GIVEN
//        productDao.add(product);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("FAILED_INSERT_PRODUCT"));
//        //WHEN-THEN
//        productDao.add(product);
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        //GIVEN
//        product = productDao.add(product);
//        //WHEN
//        int count = productDao.delete(product.getId());
//        //THEN
//        Assert.assertEquals(1, count);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageHolder.getMessageProperties()
//                .getProperty("EMPTY_RESULTSET") + Product.class);
//        productDao.getById(product.getId());
//    }
//
//    @Test
//    public void updateTest() throws Exception {
//        //GIVEN
//        product = productDao.add(product);
//        product.setPrice(2.0f);
//        product.setAvailable(false);
//        product.setDescription("new description");
//        //WHEN
//        int count = productDao.update(product);
//        Product extractedFromDb = productDao.getById(product.getId());
//        //THEN
//        Assert.assertEquals(1, count);
//        Assert.assertNotNull(extractedFromDb);
//        Assert.assertEquals(product, extractedFromDb);
//    }
//
//    @Test
//    public void getFilteredProductsTest() throws Exception {
//        //GIVEN
//        productDao.add(product);
//        Product secondProduct = new Product("second product", 2.0f, true,
//                "second description", product.getCategoryId(), product.getManufacturerId());
//        secondProduct.setCategoryName(product.getCategoryName());
//        secondProduct.setManufacturerName(product.getManufacturerName());
//        productDao.add(secondProduct);
//        productDao.add(new Product("third product", 20.0f, true,
//                "third description", product.getCategoryId(), product.getManufacturerId()));
//        productDao.add(new Product("fourth product", 1.0f, false,
//                "fourth description", product.getCategoryId(), product.getManufacturerId()));
//        //WHEN
//        Product filter = new Product();
//        filter.setAvailable(true);
//        List<Product> products = productDao.getFilteredProducts(filter, 0.0f, 3.0f
//                , OrderMethod.CHEAP_FIRST);
//        //THEN
//        Assert.assertEquals(2, products.size());
//        Assert.assertEquals(product, products.get(0));
//        Assert.assertEquals(secondProduct, products.get(1));
//    }
//}