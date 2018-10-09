//package ua.levelup.mapper.support;
//
//import org.junit.Assert;
//import org.junit.Test;
//import ua.levelup.mapper.*;
//
//public class MapperFactoryTest {
//    private AbstractMapper mapper;
//
//    @Test
//    public void getCategoryMapperTest() throws Exception {
//        //WHEN
//        mapper = MapperHolder.getMapper(MapperHolder.CATEGORY);
//        //THEN
//        Assert.assertNotNull(mapper);
//        Assert.assertEquals(CategoryMapper.class, mapper.getClass());
//    }
//
//    @Test
//    public void getManufacturerMapperTest() throws Exception {
//        //WHEN
//        mapper = MapperHolder.getMapper(MapperHolder.MANUFACTURER);
//        //THEN
//        Assert.assertNotNull(mapper);
//        Assert.assertEquals(ManufacturerMapper.class, mapper.getClass());
//    }
//
//    @Test
//    public void getProductMapperTest() throws Exception {
//        //WHEN
//        mapper = MapperHolder.getMapper(MapperHolder.PRODUCT);
//        //THEN
//        Assert.assertNotNull(mapper);
//        Assert.assertEquals(ProductMapper.class, mapper.getClass());
//    }
//
//    @Test
//    public void getOrderMapperTest() throws Exception {
//        //WHEN
//        mapper = MapperHolder.getMapper(MapperHolder.ORDER);
//        //THEN
//        Assert.assertNotNull(mapper);
//        Assert.assertEquals(OrderMapper.class, mapper.getClass());
//    }
//
//    @Test
//    public void getOrderPositionMapperTest() throws Exception {
//        //WHEN
//        mapper = MapperHolder.getMapper(MapperHolder.ORDER_POSITION);
//        //THEN
//        Assert.assertNotNull(mapper);
//        Assert.assertEquals(OrderPositionMapper.class, mapper.getClass());
//    }
//
//    @Test
//    public void getUserMapperTest() throws Exception {
//        //WHEN
//        mapper = MapperHolder.getMapper(MapperHolder.USER);
//        //THEN
//        Assert.assertNotNull(mapper);
//        Assert.assertEquals(UserMapper.class, mapper.getClass());
//    }
//}