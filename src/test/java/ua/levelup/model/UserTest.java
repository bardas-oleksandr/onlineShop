package ua.levelup.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserTest {
    private User user;

    @Before
    public void init() {
        user = new User("user","password","email",0);
        user.setId(1);
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        User other = new User("user","password","email",0);
        other.setId(1);
        //WHEN-THEN
        Assert.assertTrue(user.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        User first = new User("other user","password","email",0);
        first.setId(1);
        User second = new User("user","other password","email",0);
        second.setId(1);
        User third = new User("user","password","other email",0);
        third.setId(1);
        User fourth = new User("user","password","email",1);
        fourth.setId(1);
        User fifth = new User("user","password","email",0);
        fifth.setId(2);
        //WHEN-THEN
        Assert.assertTrue(!user.equals(first));
        Assert.assertTrue(!user.equals(second));
        Assert.assertTrue(!user.equals(third));
        Assert.assertTrue(!user.equals(fourth));
        Assert.assertTrue(!user.equals(fifth));
    }
}