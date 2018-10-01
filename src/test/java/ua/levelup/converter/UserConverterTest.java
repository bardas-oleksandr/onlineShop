package ua.levelup.converter;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.User;
import ua.levelup.model.support.UserState;
import ua.levelup.web.dto.create.UserCreateDto;
import ua.levelup.web.dto.UserDto;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UserConverterTest {
    private final static String USER_NAME = "name";
    private final static String PASSWORD = "password";
    private final static String EMAIL = "email";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void asUserTest() throws Exception {
        //GIVEN
        final UserCreateDto CREATED = new UserCreateDto(USER_NAME, PASSWORD, EMAIL, UserState.ACTIVE);
        final User EXPECTED = new User(USER_NAME, PASSWORD, EMAIL, UserState.ACTIVE.ordinal());
        //WHEN
        User user = UserConverter.asUser(CREATED);
        //THEN
        Assert.assertNotNull(user);
        Assert.assertEquals(EXPECTED, user);
    }

    @Test
    public void asUserTest_whenUserNameNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("AS_USER_CONVERTATION_ERROR"));
        //THEN
        UserConverter.asUser(new UserCreateDto(null, PASSWORD, EMAIL, UserState.ACTIVE));
    }

    @Test
    public void asUserTest_whenPasswordNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("AS_USER_CONVERTATION_ERROR"));
        //THEN
        UserConverter.asUser(new UserCreateDto(USER_NAME, null, EMAIL, UserState.ACTIVE));
    }

    @Test
    public void asUserTest_whenEmailNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("AS_USER_CONVERTATION_ERROR"));
        //THEN
        UserConverter.asUser(new UserCreateDto(USER_NAME, PASSWORD, null, UserState.ACTIVE));
    }

    @Test
    public void asUserTest_whenUserStateNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("AS_USER_CONVERTATION_ERROR"));
        //THEN
        UserConverter.asUser(new UserCreateDto(USER_NAME, PASSWORD, EMAIL, null));
    }

    @Test
    public void asUserViewDtoTest() throws Exception {
        //GIVEN
        final User USER = new User(USER_NAME, PASSWORD, EMAIL, UserState.ACTIVE.ordinal());
        final UserDto EXPECTED = new UserDto();
        EXPECTED.setUserName(USER.getUserName());
        EXPECTED.setEmail(USER.getEmail());
        EXPECTED.setState(USER.getUserState().ordinal());
        //WHEN
        UserDto userViewDto = UserConverter.asUserViewDto(USER);
        //THEN
        Assert.assertNotNull(userViewDto);
        Assert.assertEquals(EXPECTED, userViewDto);
    }

    @Test
    public void asUserViewDtoTest_whenUserNameNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("AS_USER_VIEW_DTO_CONVERTATION_ERROR"));
        //THEN
        UserConverter.asUserViewDto(new User(null, PASSWORD, EMAIL, UserState.ACTIVE.ordinal()));
    }

    @Test
    public void asUserViewDtoTest_whenPasswordNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("AS_USER_VIEW_DTO_CONVERTATION_ERROR"));
        //THEN
        UserConverter.asUserViewDto(new User(USER_NAME, null, EMAIL, UserState.ACTIVE.ordinal()));
    }

    @Test
    public void asUserViewDtoTest_whenEmailNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(MessageHolder.getMessageProperties()
                .getProperty("AS_USER_VIEW_DTO_CONVERTATION_ERROR"));
        //THEN
        UserConverter.asUserViewDto(new User(USER_NAME, PASSWORD, null, UserState.ACTIVE.ordinal()));
    }
}