package ua.levelup.model.support;

import org.junit.Assert;
import org.junit.Test;

public class OrderStateTest {
    @Test
    public void getOrderStateTest() throws Exception {
        Assert.assertEquals(OrderState.REGISTERED, OrderState.getOrderState(0));
        Assert.assertEquals(OrderState.PAYED, OrderState.getOrderState(1));
        Assert.assertEquals(OrderState.CANCELED, OrderState.getOrderState(2));
    }
}