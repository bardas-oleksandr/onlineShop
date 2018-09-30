package ua.levelup.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CategoryTest {
    private Category category;

    @Before
    public void init() {
        category = new Category("category", 1);
        category.setId(2);
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        Category other = new Category("category", 1);
        other.setId(2);
        //WHEN-THEN
        Assert.assertTrue(category.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        Category first = new Category("other category", 1);
        first.setId(2);
        Category second = new Category("category", 2);
        second.setId(2);
        Category third = new Category("category", 1);
        third.setId(3);
        //WHEN-THEN
        Assert.assertTrue(!category.equals(first));
        Assert.assertTrue(!category.equals(second));
        Assert.assertTrue(!category.equals(third));
    }
}