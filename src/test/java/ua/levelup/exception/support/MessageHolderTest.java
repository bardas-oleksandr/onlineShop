package ua.levelup.exception.support;

import org.junit.Assert;
import org.junit.Test;

import java.util.Properties;

public class MessageHolderTest {
    @Test
    public void getMessageProperties() throws Exception {
        //WHEN
        Properties properties = MessageHolder.getMessageProperties();
        //THEN
        Assert.assertNotNull(properties);
    }
}