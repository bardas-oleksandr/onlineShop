package ua.levelup.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {
    private Product product;

    @Before
    public void init() {
        product = new Product("product",1.0f,true,"description",
                1,1);
        product.setId(1);
        product.setCategoryName("category");
        product.setManufacturerName("manufacturer");
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        Product other = new Product("product",1.0f,true,"description",
                1,1);
        other.setId(1);
        other.setCategoryName("category");
        other.setManufacturerName("manufacturer");
        //WHEN-THEN
        Assert.assertTrue(product.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        Product first = new Product("other product",1.0f,true,"description",
                1,1);
        first.setId(1);
        first.setCategoryName("category");
        first.setManufacturerName("manufacturer");
        Product second = new Product("product",1.1f,true,"description",
                1,1);
        second.setId(1);
        second.setCategoryName("category");
        second.setManufacturerName("manufacturer");
        Product third = new Product("product",1.0f,false,"description",
                1,1);
        third.setId(1);
        third.setCategoryName("category");
        third.setManufacturerName("manufacturer");
        Product fourth = new Product("product",1.0f,true,"other description",
                1,1);
        fourth.setId(1);
        fourth.setCategoryName("category");
        fourth.setManufacturerName("manufacturer");
        Product fifth = new Product("product",1.0f,true,"description",
                2,1);
        fifth.setId(1);
        fifth.setCategoryName("category");
        fifth.setManufacturerName("manufacturer");
        Product sixth = new Product("product",1.0f,true,"description",
                1,2);
        sixth.setId(1);
        sixth.setCategoryName("category");
        sixth.setManufacturerName("manufacturer");
        Product seventh = new Product("product",1.0f,true,"description",
                1,1);
        seventh.setId(2);
        seventh.setCategoryName("category");
        seventh.setManufacturerName("manufacturer");
        Product eighth = new Product("product",1.0f,true,"description",
                1,1);
        eighth.setId(1);
        eighth.setCategoryName("other category");
        eighth.setManufacturerName("manufacturer");
        Product ninth = new Product("product",1.0f,true,"description",
                1,1);
        ninth.setId(1);
        ninth.setCategoryName("category");
        ninth.setManufacturerName("other manufacturer");
        //WHEN-THEN
        Assert.assertTrue(!product.equals(first));
        Assert.assertTrue(!product.equals(second));
        Assert.assertTrue(!product.equals(third));
        Assert.assertTrue(!product.equals(fourth));
        Assert.assertTrue(!product.equals(fifth));
        Assert.assertTrue(!product.equals(sixth));
        Assert.assertTrue(!product.equals(seventh));
        Assert.assertTrue(!product.equals(eighth));
        Assert.assertTrue(!product.equals(ninth));
    }
}