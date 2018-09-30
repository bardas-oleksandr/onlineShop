package ua.levelup.service;

import ua.levelup.model.User;
import ua.levelup.model.support.UserState;
import ua.levelup.service.impl.SecurityServiceImpl;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class SecurityServiceImplTest {
    private SecurityService securityService = new SecurityServiceImpl();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void isCorrectPasswordTest() throws Exception {
        //GIVEN
        final String PASSWORD = "password";
        User first = new User("user", "password", "email", UserState.ACTIVE.ordinal());
        User second = new User("user", "other password", "email", UserState.ACTIVE.ordinal());
        //WHEN-THEN
        Assert.assertTrue(securityService.isCorrectPassword(first, PASSWORD));
        Assert.assertTrue(!securityService.isCorrectPassword(second, PASSWORD));
        Assert.assertTrue(!securityService.isCorrectPassword(first, null));
    }

    @Test
    public void isCorrectPasswordTest_whenUsersPasswordEqualsNull_thenException() throws Exception {
        //GIVEN
        final String PASSWORD = "password";
        User user = new User("userName", null, "email", UserState.ACTIVE.ordinal());
        expectedException.expect(NullPointerException.class);
        //WHEN-THEN
        securityService.isCorrectPassword(user, PASSWORD);
    }
}