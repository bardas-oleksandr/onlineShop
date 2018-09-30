package ua.levelup.config;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class PropertiesManagerTest {
    @Test
    public void getApplicationPropertiesTest() throws Exception {
        //WHEN
        Properties properties = PropertiesManager.getApplicationProperties();
        //THEN
        Assert.assertNotNull(properties);
    }
}