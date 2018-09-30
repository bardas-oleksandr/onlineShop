package ua.levelup.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ManufacturerTest {
    private Manufacturer manufacturer;

    @Before
    public void init() {
        manufacturer = new Manufacturer("manufacturer");
        manufacturer.setId(1);
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        Manufacturer other = new Manufacturer("manufacturer");
        other.setId(1);
        //WHEN-THEN
        Assert.assertTrue(manufacturer.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        Manufacturer first = new Manufacturer("other manufacturer");
        first.setId(1);
        Manufacturer second = new Manufacturer("manufacturer");
        second.setId(2);
        //WHEN-THEN
        Assert.assertTrue(!manufacturer.equals(first));
        Assert.assertTrue(!manufacturer.equals(second));
    }
}