//package ua.levelup.dao.impl;
//
//import ua.levelup.dao.UserDao;
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.model.User;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//import java.sql.Connection;
//import java.util.List;
//
//public class UserDaoImplTest extends AbstractDaoImplTest {
//
//    private static final String NAME = "John";
//    private static final String PASSWORD = "password";
//    private static final String EMAIL = "john@gmail.com";
//    private static final int STATE = 0;
//
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    private UserDao userDao;
//    private User user;
//
//    @Before
//    public void init() {
//        Connection connection = getConnection();
//        userDao = new UserDaoImpl(getConnection());
//        user = new User(NAME, PASSWORD, EMAIL, STATE);
//    }
//
//    @Test
//    public void addAndGetByNameTest() throws Exception {
//        //WHEN
//        User returnedUser = userDao.add(user);
//        User extractedFromDbUser = userDao.getById(returnedUser.getId());
//        //THEN
//        Assert.assertNotNull(returnedUser);
//        Assert.assertNotNull(extractedFromDbUser);
//        Assert.assertSame(user, returnedUser);
//        Assert.assertEquals(user, extractedFromDbUser);
//    }
//
//    @Test
//    public void addAndGetByEmailTest() throws Exception {
//        //WHEN
//        User returnedUser = userDao.add(user);
//        User extractedFromDbUser = userDao.getByEmail(EMAIL);
//        //THEN
//        Assert.assertNotNull(returnedUser);
//        Assert.assertNotNull(extractedFromDbUser);
//        Assert.assertSame(user, returnedUser);
//        Assert.assertEquals(user, extractedFromDbUser);
//    }
//
//    @Test
//    public void addTest_whenUserNameNull_thenException() throws Exception {
//        //GIVEN
//        user = new User(null, PASSWORD, "newemail@gmail.com", STATE);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageContainer.getMessageProperties()
//                .getProperty("FAILED_INSERT_USER"));
//        //WHEN-THEN
//        userDao.add(user);
//    }
//
//    @Test
//    public void addTest_whenPasswordNull_thenException() throws Exception {
//        //GIVEN
//        user = new User(NAME, null, "newemail@gmail.com", STATE);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageContainer.getMessageProperties()
//                .getProperty("FAILED_INSERT_USER"));
//        //WHEN-THEN
//        userDao.add(user);
//    }
//
//    @Test
//    public void addTest_whenEmailNull_thenException() throws Exception {
//        //GIVEN
//        user = new User(NAME, PASSWORD, null, STATE);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageContainer.getMessageProperties()
//                .getProperty("FAILED_INSERT_USER"));
//        //WHEN-THEN
//        userDao.add(user);
//    }
//
//    @Test
//    public void addTest_whenNotUniqueUserName_thenException() throws Exception {
//        //GIVEN
//        user = new User(NAME, PASSWORD, "newemail@gmail.com", STATE);
//        userDao.add(user);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageContainer.getMessageProperties()
//                .getProperty("FAILED_INSERT_USER"));
//        //WHEN-THEN
//        userDao.add(user);
//    }
//
//    @Test
//    public void addTest_whenNotUniqueEmail_thenException() throws Exception {
//        //GIVEN
//        user = new User("newName", PASSWORD, EMAIL, STATE);
//        userDao.add(user);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageContainer.getMessageProperties()
//                .getProperty("FAILED_INSERT_USER"));
//        //WHEN-THEN
//        userDao.add(user);
//    }
//
//    @Test
//    public void deleteTest() throws Exception {
//        //GIVEN
//        user = userDao.add(user);
//        //WHEN
//        int count = userDao.delete(user.getId());
//        //THEN
//        Assert.assertEquals(1,count);
//        expectedException.expect(ApplicationException.class);
//        expectedException.expectMessage(MessageContainer.getMessageProperties()
//                .getProperty("EMPTY_RESULTSET") + User.class);
//        User extractedFromDb = userDao.getByEmail(user.getEmail());
//    }
//
//    @Test
//    public void updateTest() throws Exception {
//        //GIVEN
//        userDao.add(user);
//        user.setUserName("Bob");
//        user.setPassword("1234");
//        user.setEmail("bob@gmail.com");
//        user.setUserState(1);
//        //WHEN
//        int count = userDao.update(user.getId(), user);
//        User extractedFromDb = userDao.getByEmail(user.getEmail());
//        user.setId(extractedFromDb.getId());
//        //THEN
//        Assert.assertEquals(1,count);
//        Assert.assertNotNull(extractedFromDb);
//        Assert.assertEquals(user, extractedFromDb);
//    }
//
//    @Test
//    public void getAllUsersTest() throws Exception {
//        //GIVEN
//        User secondUser = new User("Jack","1234","jack@gmail.com",1);
//        userDao.add(user);
//        userDao.add(secondUser);
//        //WHEN
//        List<User> users = userDao.getAllUsers();
//        //THEN
//        Assert.assertEquals(2,users.size());
//        Assert.assertEquals(user,users.get(0));
//        Assert.assertEquals(secondUser,users.get(1));
//    }
//}