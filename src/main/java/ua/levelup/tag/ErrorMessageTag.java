//package ua.levelup.tag;
//
//import ua.levelup.exception.ApplicationException;
//import ua.levelup.exception.ValidationException;
//import ua.levelup.exception.support.MessageContainer;
//import ua.levelup.model.User;
//import ua.levelup.util.ResourceBundleHolder;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//
//import javax.servlet.jsp.JspException;
//import javax.servlet.jsp.tagext.TagSupport;
//import java.io.IOException;
//import java.util.*;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class ErrorMessageTag extends TagSupport {
//
//    private static final Logger logger = LogManager.getLogger();
//    private static Properties properties = MessageContainer.getMessageProperties();
//
//    private Exception exception;
//    private Locale locale;
//    private ResourceBundle resourceBundle;
//
//    @Override
//    public int doStartTag() throws JspException {
//        logger.error("Message: " + exception.getMessage() + "\tCause: " + exception.getCause());
//        exception.printStackTrace();
//        try {
//            if (locale == null) {
//                locale = Locale.US;
//            }
//            resourceBundle = ResourceBundleHolder.getBundle(locale);
//            String errorMessage;
//            if (exception != null) {
//                String message = exception.getMessage();
//                if (exception.getClass() == ValidationException.class) {
//                    errorMessage = getMessageOnValidationException(message);
//                } else if (exception.getClass() == ApplicationException.class) {
//                    errorMessage = getMessageOnApplicationException(message);
//                } else {
//                    errorMessage = resourceBundle.getString("nothing_have_to_do_with_problem");
//                }
//            } else {
//                errorMessage = resourceBundle.getString("nothing_have_to_do_with_problem");
//            }
//            pageContext.getOut().write(errorMessage);
//        } catch (MissingResourceException e) {
//            try {
//                pageContext.getOut().write("Some problems with localization have occurred");
//            } catch (IOException e1) {
//                e1.printStackTrace();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return SKIP_BODY;
//    }
//
//    @Override
//    public void release() {
//        exception = null;
//    }
//
//    public Exception getException() {
//        return exception;
//    }
//
//    public void setException(Exception exception) {
//        this.exception = exception;
//    }
//
//    public Locale getLocale() {
//        return locale;
//    }
//
//    public void setLocale(Locale locale) {
//        this.locale = locale;
//    }
//
//    private String getMessageOnValidationException(String message) {
//        Optional<String> messageKey = properties.stringPropertyNames().stream()
//                .filter((errorKey) -> message.contains(properties.getProperty(errorKey)))
//                .findFirst();
//        if (messageKey.isPresent()) {
//            String errorMessage = resourceBundle.getString(messageKey.get().toLowerCase());
//            if (messageKey.equals("INVALID_EMAIL_FORMAT")) {
//                errorMessage = errorMessage + extractEmail(message);
//            }
//            return errorMessage;
//        } else {
//            return resourceBundle.getString("unknown_validation_exception");
//        }
//    }
//
//    private String getMessageOnApplicationException(String message) {
//        Optional<String> messageKey = properties.stringPropertyNames().stream()
//                .filter((errorKey) -> message.contains(properties.getProperty(errorKey)))
//                .findFirst();
//        if (messageKey.isPresent()) {
//            return resourceBundle.getString(messageKey.get().toLowerCase());
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
//}