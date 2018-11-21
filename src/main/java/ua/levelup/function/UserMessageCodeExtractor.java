package ua.levelup.function;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.ValidationException;
import ua.levelup.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserMessageCodeExtractor {

    private static final Logger logger = LogManager.getLogger();

    public String getUserMessageCode(Exception exception) {
        return "nothing_have_to_do_with_problem";
//        String exceptionMessageCode;
//        if (exception != null) {
//            String message = exception.getMessage();
//            if (exception.getClass() == ValidationException.class) {
//                exceptionMessageCode = getMessageOnValidationException(message);
//            } else if (exception.getClass() == ApplicationException.class) {
//                exceptionMessageCode = getMessageOnApplicationException(message);
//            } else {
//                exceptionMessageCode = "nothing_have_to_do_with_problem";
//            }
//        } else {
//            exceptionMessageCode = "nothing_have_to_do_with_problem";
//        }
//        return exceptionMessageCode;
    }

//    private String getMessageOnValidationException(String message) {
//        Optional<String> messageKey = properties.stringPropertyNames().stream()
//                .filter((errorKey) -> message.contains(properties.getProperty(errorKey)))
//                .findFirst();
//        if (messageKey.isPresent()) {
//            String errorMessage = messageKey.get().toLowerCase();
//            if (messageKey.equals("INVALID_EMAIL_FORMAT")) {
//                errorMessage = errorMessage + extractEmail(message);
//            }
//            return errorMessage;
//        } else {
//            return "unknown_validation_exception";
//        }
//    }
//
//    private String getMessageOnApplicationException(String message) {
//        Optional<String> messageKey = properties.stringPropertyNames().stream()
//                .filter((errorKey) -> message.contains(properties.getProperty(errorKey)))
//                .findFirst();
//        if (messageKey.isPresent()) {
//            return messageKey.get().toLowerCase();
//        } else {
//            if ((properties.getProperty("EMPTY_RESULTSET") + User.class).equals(message)) {
//                return resourceBundle.getString("user_does_not_exist");
//            }
//            return resourceBundle.getString("nothing_have_to_do_with_problem");
//        }
//    }
//
//    private String extractEmail(String message) {
//        Pattern pattern = Pattern.compile("(" + properties.getProperty("INVALID_EMAIL_FORMAT") + ")(.+)");
//        Matcher matcher = pattern.matcher(message);
//        if (matcher.find()) {
//            return matcher.group(2);
//        }
//        throw new IllegalArgumentException("Invalid message format");
//    }
}