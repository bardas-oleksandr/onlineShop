package ua.levelup.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

public class OrderTest {
    private static final Timestamp TIMESTAMP = new Timestamp(System.currentTimeMillis());

    private Order order;

    @Before
    public void init() {
        order = new Order(1,1,TIMESTAMP,"address",1);
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        Order other = new Order(1,1,TIMESTAMP,"address",1);
        //WHEN-THEN
        Assert.assertTrue(order.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        Order first = new Order(2,1,TIMESTAMP,"address",1);
        Order second = new Order(1,2,TIMESTAMP,"address",1);
        Order third = new Order(1,1,new Timestamp(1),"address",1);
        Order fourth = new Order(1,1,TIMESTAMP,"other address",1);
        Order fifth = new Order(1,1,TIMESTAMP,"address",0);
        //WHEN-THEN
        Assert.assertTrue(!order.equals(first));
        Assert.assertTrue(!order.equals(second));
        Assert.assertTrue(!order.equals(third));
        Assert.assertTrue(!order.equals(fourth));
        Assert.assertTrue(!order.equals(fifth));
    }
}