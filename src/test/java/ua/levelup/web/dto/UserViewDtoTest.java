package ua.levelup.web.dto;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class UserViewDtoTest {
    private UserDto userViewDto;

    @Before
    public void init() {
        userViewDto = new UserDto();
        userViewDto.setId(1);
        userViewDto.setUserName("user");
        userViewDto.setEmail("email");
        userViewDto.setState(0);
    }

    @Test
    public void equalsTest_whenEqualObjects_thenTrue() throws Exception {
        //GIVEN
        UserDto other = new UserDto();
        other.setId(1);
        other.setUserName("user");
        other.setEmail("email");
        other.setState(0);
        //WHEN-THEN
        Assert.assertTrue(userViewDto.equals(other));
    }

    @Test
    public void equalsTest_whenNotEqualObjects_thenFalse() throws Exception {
        //GIVEN
        UserDto first = new UserDto();
        first.setId(2);
        first.setUserName("user");
        first.setEmail("email");
        first.setState(0);
        UserDto second = new UserDto();
        second.setId(1);
        second.setUserName("other user");
        second.setEmail("email");
        second.setState(0);
        UserDto third = new UserDto();
        third.setId(1);
        third.setUserName("user");
        third.setEmail("other email");
        third.setState(0);
        UserDto fourth = new UserDto();
        fourth.setId(1);
        fourth.setUserName("user");
        fourth.setEmail("email");
        fourth.setState(1);
        //WHEN-THEN
        Assert.assertTrue(!userViewDto.equals(first));
        Assert.assertTrue(!userViewDto.equals(second));
        Assert.assertTrue(!userViewDto.equals(third));
        Assert.assertTrue(!userViewDto.equals(fourth));
        Assert.assertTrue(!userViewDto.equals(null));
    }
}