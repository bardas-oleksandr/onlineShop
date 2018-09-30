package ua.levelup.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class OrderPositionTest {
    private OrderPosition orderPosition;

    @Before
    public void init() {
        orderPosition = new OrderPosition(1,1,"name",
                3,4.0f);
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        OrderPosition other = new OrderPosition(1,1,"name",
                3,4.0f);
        //WHEN-THEN
        Assert.assertTrue(orderPosition.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        OrderPosition first = new OrderPosition(11,1,"name",3,4.0f);
        OrderPosition second = new OrderPosition(1,11,"name",3,4.0f);
        OrderPosition third = new OrderPosition(1,1,"other name",3,4.0f);
        OrderPosition fourth = new OrderPosition(1,1,"name",33,4.0f);
        OrderPosition fifth = new OrderPosition(1,1,"name",3,44.0f);
        //WHEN-THEN
        Assert.assertTrue(!orderPosition.equals(first));
        Assert.assertTrue(!orderPosition.equals(second));
        Assert.assertTrue(!orderPosition.equals(third));
        Assert.assertTrue(!orderPosition.equals(fourth));
        Assert.assertTrue(!orderPosition.equals(fifth));
    }
}