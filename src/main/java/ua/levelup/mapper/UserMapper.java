//package ua.levelup.mapper;
//
//import ua.levelup.model.User;
//
//import java.sql.ResultSet;
//import java.sql.SQLException;
//
//public class UserMapper extends AbstractMapper<User> {
//
//    private final static String ID = "id";
//    private final static String NAME = "user_name";
//    private final static String PASSWORD = "user_password";
//    private final static String EMAIL = "user_email";
//    private final static String STATE = "user_state";
//
//    @Override
//    protected User getObject(ResultSet resultSet) throws SQLException {
//        User user = new User();
//        user.setId(resultSet.getInt(ID));
//        user.setUserName(resultSet.getString(NAME));
//        user.setPassword(resultSet.getString(PASSWORD));
//        user.setEmail(resultSet.getString(EMAIL));
//        user.setUserState(resultSet.getInt(STATE));
//        return user;
//    }
//}
