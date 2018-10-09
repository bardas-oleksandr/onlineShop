//package ua.levelup.validator;
//
//import ua.levelup.exception.ValidationException;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.rules.ExpectedException;
//
//public class UserValidatorTest {
//    @Rule
//    public ExpectedException expectedException = ExpectedException.none();
//
//    @Test
//    public void validateUsersCredentialsTest_whenEmptyPassword_thenException() throws Exception {
//        //GIVEN
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid password: empty value");
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials("email@gmail.com","");
//    }
//
//    @Test
//    public void validateUsersCredentialsTest_whenLongPassword_thenException() throws Exception {
//        //GIVEN
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid password. Length > 20");
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials("email@gmail.com","zzzzzzzzzzzzzzzzzzzzz");
//    }
//
//    @Test
//    public void validateUsersCredentialsTest_whenEmptyEmail_thenException() throws Exception {
//        //GIVEN
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid email: empty value");
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials("","password");
//    }
//
//    @Test
//    public void validateUsersCredentialsTest_whenLongEmail_thenException() throws Exception {
//        //GIVEN
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid email. Length > 320");
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials("qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"+
//                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"+
//                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"+
//                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq"+
//                "qqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq","password");
//    }
//
//    @Test
//    public void validateUsersCredentialsTest_whenEmailHasNoArobaseSymbol_thenException() throws Exception {
//        //GIVEN
//        final String EMAIL = "mail.gmail.ua";
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid email format:" + EMAIL);
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials(EMAIL,"password");
//    }
//
//    @Test
//    public void validateUsersCredentialsTest_whenEmailHasNoExtension_thenException() throws Exception {
//        //GIVEN
//        final String EMAIL = "mail@gmail";
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid email format:" + EMAIL);
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials(EMAIL,"password");
//    }
//
//    @Test
//    public void validateUsersCredentialsTest_whenEmailHasNoLocalPart_thenException() throws Exception {
//        //GIVEN
//        final String EMAIL = "@gmail.com";
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid email format:" + EMAIL);
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials(EMAIL,"password");
//    }
//
//    @Test
//    public void validateUsersCredentialsTest_whenEmailHasNoDomainPart_thenException() throws Exception {
//        //GIVEN
//        final String EMAIL = "mail@";
//        expectedException.expect(ValidationException.class);
//        expectedException.expectMessage("Invalid email format:" + EMAIL);
//        //WHEN-THEN
//        UserValidator.validateUsersCredentials(EMAIL,"password");
//    }
//}