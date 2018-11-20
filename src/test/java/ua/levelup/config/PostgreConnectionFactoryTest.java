//package ua.levelup.config;
//
//import org.apache.commons.pool2.PooledObject;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import ua.levelup.dao.factory.ConnectionFactory;
//import ua.levelup.testconfig.TestContextConfig;
//
//import java.sql.Connection;
//import java.util.Properties;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = {TestContextConfig.class})
//@ActiveProfiles("test")
//public class ConnectionFactoryTest {
//
//    @Autowired
//    private Properties applicationProperties;
//
//    @Mock
//    private Properties applicationProps;
//
//    @InjectMocks
//    private ConnectionFactory connectionFactory;
//
//    @Before
//    public void init() {
//        MockitoAnnotations.initMocks(this);
//        Mockito.when(applicationProps.getProperty("driver"))
//                .thenReturn(applicationProperties.getProperty("test.driver"));
//        Mockito.when(applicationProps.getProperty("url"))
//                .thenReturn(applicationProperties.getProperty("test.url"));
//        Mockito.when(applicationProps.getProperty("username"))
//                .thenReturn(applicationProperties.getProperty("test.username"));
//        Mockito.when(applicationProps.getProperty("password"))
//                .thenReturn(applicationProperties.getProperty("test.password"));
//    }
//
//    @Test
//    public void createTest() throws Exception {
//        //WHEN
//        Connection connection = connectionFactory.create();
//        //THEN
//        Assert.assertNotNull(connection);
//        Mockito.verify(applicationProps, Mockito.times(1))
//                .getProperty("driver");
//        Mockito.verify(applicationProps, Mockito.times(1))
//                .getProperty("url");
//        Mockito.verify(applicationProps, Mockito.times(1))
//                .getProperty("username");
//        Mockito.verify(applicationProps, Mockito.times(1))
//                .getProperty("password");
//        Mockito.verifyNoMoreInteractions(applicationProps);
//    }
//
//    @Test
//    public void wrapTest() throws Exception {
//        //GIVEN
//        Connection connection = connectionFactory.create();
//        //WHEN
//        PooledObject<Connection> pooledObject = connectionFactory.wrap(connection);
//        Connection otherReference = pooledObject.getObject();
//        //THEN
//        Assert.assertNotNull(pooledObject);
//        Assert.assertSame(connection, otherReference);
//    }
//
//    @Test
//    public void destroyObjectTest() throws Exception {
//        //GIVEN
//        Connection connection = connectionFactory.create();
//        PooledObject<Connection> pooledObject = connectionFactory.wrap(connection);
//        //WHEN
//        connectionFactory.destroyObject(pooledObject);
//        //THEN
//        Assert.assertNotNull(connection);
//        Assert.assertTrue(connection.isClosed());
//    }
//}