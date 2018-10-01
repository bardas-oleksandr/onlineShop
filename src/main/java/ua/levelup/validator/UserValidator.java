package ua.levelup.validator;

import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import org.apache.commons.lang3.StringUtils;
import ua.levelup.model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserValidator {
    ;

    public static void validateNewUser(User user) {
        validateUsersCredentials(user.getEmail(), user.getPassword());
        validateUserName(user.getUserName());
    }

    public static void validateUsersCredentials(String email, String password) {
        final int EMAIL_MAX_LENGTH = 320;
        final int PASSWORD_MAX_LENGTH = 20;
        if (StringUtils.isEmpty(email)) {
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_EMAIL"));
        }
        if (StringUtils.isEmpty(password)) {
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_PASSWORD"));
        }
        if (email.length() > EMAIL_MAX_LENGTH) {
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("LONG_EMAIL") + EMAIL_MAX_LENGTH);
        }
        if (password.length() > PASSWORD_MAX_LENGTH) {
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("LONG_PASSWORD") + PASSWORD_MAX_LENGTH);
        }
        validateEmailFormat(email);
    }

    private static void validateUserName(String userName){
        final int USER_NAME_MAX_LENGTH = 20;
        if (StringUtils.isEmpty(userName)) {
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("EMPTY_USER_NAME"));
        }
        if (userName.length() > USER_NAME_MAX_LENGTH) {
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("LONG_USER_NAME") + USER_NAME_MAX_LENGTH);
        }
    }

    private static void validateEmailFormat(String email) {
        Pattern pattern = Pattern.compile("^[\\w]+(\\.[\\w]+)*@[\\w]+(\\.[\\w]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new ValidationException(MessageHolder.getMessageProperties()
                    .getProperty("INVALID_EMAIL_FORMAT") + email);
        }
    }
}
