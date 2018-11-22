package ua.levelup.dao.impl;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ua.levelup.dao.UserDao;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import ua.levelup.exception.ApplicationException;
import ua.levelup.model.User;
import ua.levelup.testconfig.TestContextConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/*Класс UserDaoImplTest содержит интеграционные тесты для проверки
* корректности работы слоя доступа к данным, относящимся к сущности
* "Пользователь"
*
* Автор: Бардась А.А.
* */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestContextConfig.class})
@ActiveProfiles("test")
public class UserDaoImplTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private UserDao userDao;

    @Autowired
    private Properties messagesProperties;

    /*Сценарий: - добавление в базу данных информации о пользователе;
    *           - все поля корректны, пользователь уникален
    * Результат: пользователь успешно добавлен в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void addTest_whenUserIsUnique_thenOk() throws Exception {
        //GIVEN
        User user = new User("new name", "password", "mail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        //WHEN
        userDao.add(user);
        //THEN
        User extracted = userDao.getById(user.getId());
        Assert.assertNotNull(extracted);
        Assert.assertEquals(user, extracted);
    }

    /*Сценарий: - добавление в базу данных информации о пользователе;
    *           - имя пользователя не уникально
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void addTest_whenUserNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        User user = new User("first user", "password", "mail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - добавление в базу данных информации о пользователе;
    *           - email пользователя не уникален
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void addTest_whenEmailIsNotUnique_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", "password", "firstmail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - добавление в базу данных информации о пользователе;
    *           - имя пользователя == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void addTest_whenNameEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User(null, "password", "mail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - добавление в базу данных информации о пользователе;
    *           - пароль == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void addTest_whenPasswordEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", null, "mail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - добавление в базу данных информации о пользователе;
    *           - email == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void addTest_whenEmailEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", "password", null);
        user.setUserState(User.UserState.ACTIVE);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - добавление в базу данных информации о пользователе;
    *           - объект вставляемого пользователя равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void addTest_whenUserEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("user is marked @NonNull but is null");
        //THEN
        userDao.add(null);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе
    *            включая пароль пользователя;
    *           - все поля корректны, пользователь уникален
    * Результат: пользователь успешно добавлен в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateWithPasswordTest_whenUserIsUnique_thenOk() throws Exception {
        //GIVEN
        User user = new User("new name", "new password", "newmail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        //WHEN
        userDao.updateWithPassword(user);
        //THEN
        User extracted = userDao.getById(user.getId());
        Assert.assertNotNull(extracted);
        Assert.assertEquals(user, extracted);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе
    *            включая пароль пользователя;
    *           - имя пользователя не уникально
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateWithPasswordTest_whenUserNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        User user = new User("second user", "new password", "newmail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_USER"));
        //WHEN-THEN
        userDao.updateWithPassword(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе
    *            включая пароль пользователя;
    *           - email пользователя не уникален
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateWithPasswordTest_whenEmailIsNotUnique_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", "password", "secondmail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе
    *            включая пароль пользователя;
    *           - имя пользователя == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateWithPasswordTest_whenNameEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User(null, "password", "mail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе;
    *            включая пароль пользователя;
    *           - пароль == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateWithPasswordTest_whenPasswordEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", null, "mail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе;
    *            включая пароль пользователя;
    *           - email == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateWithPasswordTest_whenEmailEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", "password", null);
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе;
    *            включая пароль пользователя;
    *           - модифицируемый объект равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void updateWithPasswordTest_whenUserEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("user is marked @NonNull but is null");
        //THEN
        userDao.updateWithPassword(null);
    }







    /*Сценарий: - модификация в базе данных информации о пользователе
    *            пароль пользователя не изменяется;
    *           - все поля корректны, пользователь уникален
    * Результат: пользователь успешно добавлен в базу данных.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_whenUserIsUnique_thenOk() throws Exception {
        //GIVEN
        User user = userDao.getById(1);
        user.setUserName("new name");
        user.setEmail("newmail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        //WHEN
        userDao.updateWithPassword(user);
        //THEN
        User extracted = userDao.getById(user.getId());
        Assert.assertNotNull(extracted);
        Assert.assertEquals(user, extracted);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе
    *            пароль пользователя не изменяется;
    *           - имя пользователя не уникально
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_whenUserNameIsNotUnique_thenException() throws Exception {
        //GIVEN
        User user = new User("second user", "new password", "newmail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_USER"));
        //WHEN-THEN
        userDao.updateWithPassword(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе
    *            пароль пользователя не изменяется;
    *           - email пользователя не уникален
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_whenEmailIsNotUnique_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", "password", "secondmail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("NOT_UNIQUE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе
    *            пароль пользователя не изменяется;
    *           - имя пользователя == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_whenNameEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User(null, "password", "mail@gmail.com");
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе;
    *            пароль пользователя не изменяется;
    *           - email == null
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void updateTest_whenEmailEqualsNull_thenException() throws Exception {
        //GIVEN
        User user = new User("new user", "password", null);
        user.setUserState(User.UserState.ACTIVE);
        user.setId(1);
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_SAVE_USER"));
        //WHEN-THEN
        userDao.add(user);
    }

    /*Сценарий: - модификация в базе данных информации о пользователе;
    *            пароль пользователя не изменяется;
    *           - модифицируемый объект равен null.
    * Результат: исключение NullPointerException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void updateTest_whenUserEqualsNull_thenException() throws Exception {
        //GIVEN
        expectedException.expect(NullPointerException.class);
        expectedException.expectMessage("user is marked @NonNull but is null");
        //THEN
        userDao.updateWithPassword(null);
    }

















    /*Сценарий: - удаление из базы данных информации о пользователе;
    *             пользователь существует.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void deleteTest_whenUserExists_thenOk() throws Exception {
        //GIVEN
        final int USER_ID = 1;
        User user = userDao.getById(USER_ID);
        //WHEN
        userDao.delete(USER_ID);
        //THEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + User.class);
        userDao.getById(USER_ID);
    }

    /*Сценарий: - удаление из базы данных информации о пользователе;
    *             пользователь не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void deleteTest_whenUserNonexistent_thenException() throws Exception {
        //GIVEN
        final int USER_ID = 10;
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("FAILED_DELETE_USER_NONEXISTENT"));
        //WHEN-THEN
        userDao.delete(USER_ID);
    }

    /*Сценарий: - получение из базы данных информации о пользователе;
    *             пользователь существует.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void getByIdTest_whenUserExists_thenOk() throws Exception {
        //GIVEN
        final int USER_ID = 1;
        User user = new User("first user", "password", "firstmail@gmail.com");
        user.setUserState(User.UserState.ADMIN);
        user.setId(USER_ID);
        //WHEN
        User extracted = userDao.getById(USER_ID);
        //THEN
        Assert.assertNotNull(extracted);
        Assert.assertEquals(user, extracted);
    }

    /*Сценарий: - получение из базы данных информации о пользователе;
    *             пользователь не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void getByIdTest_whenUserNonexistent_thenException() throws Exception {
        //GIVEN
        final int USER_ID = 10;
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + User.class);
        //WHEN-THEN
        userDao.getById(USER_ID);
    }

    /*Сценарий: - получение из базы данных информации о пользователе;
    *             пользователь существует.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void getByEmailTest_whenUserExists_thenOk() throws Exception {
        //GIVEN
        User userById = userDao.getById(1);
        //WHEN
        User userByEmail = userDao.getByEmail("firstmail@gmail.com");
        //THEN
        Assert.assertNotNull(userByEmail);
        Assert.assertEquals(userById, userByEmail);
    }

    /*Сценарий: - получение из базы данных информации о пользователе;
    *             пользователь не существует.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void getByEmailTest_whenUserNonexistent_thenException() throws Exception {
        //GIVEN
        expectedException.expect(ApplicationException.class);
        expectedException.expectMessage(messagesProperties
                .getProperty("EMPTY_RESULTSET") + User.class);
        //WHEN-THEN
        userDao.getByEmail("nonexistent@gmail.com");
    }

    /*Сценарий: - получение из базы данных информации обо всех пользователях;
    *             пользователи существуют.
    * Результат: операция выполнена успешно.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql", "classpath:schema_insert_userTest.sql"})
    public void getAllUsersTest_whenUsersExist_thenOk() throws Exception {
        //GIVEN
        List<User> expected = new ArrayList<>();
        expected.add(userDao.getById(1));
        expected.add(userDao.getById(2));
        expected.add(userDao.getById(3));
        //WHEN
        List<User> userList = userDao.getAllUsers();
        //THEN
        Assert.assertNotNull(userList);
        Assert.assertEquals(expected.size(), userList.size());
        Assert.assertTrue(expected.contains(userList.get(0)));
        Assert.assertTrue(expected.contains(userList.get(1)));
        Assert.assertTrue(expected.contains(userList.get(2)));
    }

    /*Сценарий: - получение из базы данных информации обо всех пользователях;
    *             пользователи не существуют.
    * Результат: исключение ApplicationException.
    * */
    @Test
    @Sql({"classpath:schema_clean.sql"})
    public void getAllUsersTest_whenUsersNonexistent_thenException() throws Exception {
        //WHEN
        List<User> userList = userDao.getAllUsers();
        //THEN
        Assert.assertEquals(0, userList.size());
    }
}