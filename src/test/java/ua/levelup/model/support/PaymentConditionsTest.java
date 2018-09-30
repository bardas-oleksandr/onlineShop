package ua.levelup.model.support;

import org.junit.Assert;
import org.junit.Test;

public class PaymentConditionsTest {
    @Test
    public void getPaymentConditionsTest() throws Exception {
        Assert.assertEquals(PaymentConditions.CASH, PaymentConditions.getPaymentConditions(0));
        Assert.assertEquals(PaymentConditions.CARD, PaymentConditions.getPaymentConditions(1));
    }
}