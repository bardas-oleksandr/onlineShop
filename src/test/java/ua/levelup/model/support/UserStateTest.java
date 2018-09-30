package ua.levelup.model.support;

import org.junit.Assert;
import org.junit.Test;

public class UserStateTest {
    @Test
    public void getUserStateTest() throws Exception {
        Assert.assertEquals(UserState.ADMIN, UserState.getUserState(0));
        Assert.assertEquals(UserState.ACTIVE, UserState.getUserState(1));
        Assert.assertEquals(UserState.BLOCKED, UserState.getUserState(2));
    }
}