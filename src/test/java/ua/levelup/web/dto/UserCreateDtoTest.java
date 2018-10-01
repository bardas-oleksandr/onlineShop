package ua.levelup.web.dto;

import ua.levelup.model.support.UserState;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ua.levelup.web.dto.create.UserCreateDto;

public class UserCreateDtoTest {
    private UserCreateDto userCreateDto;

    @Before
    public void init() {
        userCreateDto = new UserCreateDto("user", "password","email", UserState.ACTIVE);
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        UserCreateDto other = new UserCreateDto("user", "password","email", UserState.ACTIVE);
        //WHEN-THEN
        Assert.assertTrue(userCreateDto.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        UserCreateDto first = new UserCreateDto("other user", "password","email", UserState.ACTIVE);
        UserCreateDto second = new UserCreateDto("user", "other password","email", UserState.ACTIVE);
        UserCreateDto third = new UserCreateDto("user", "password","other email", UserState.ACTIVE);
        UserCreateDto fourth = new UserCreateDto("user", "password","email", UserState.ADMIN);
        //WHEN-THEN
        Assert.assertTrue(!userCreateDto.equals(first));
        Assert.assertTrue(!userCreateDto.equals(second));
        Assert.assertTrue(!userCreateDto.equals(third));
        Assert.assertTrue(!userCreateDto.equals(fourth));
        Assert.assertTrue(!userCreateDto.equals(null));
    }
}