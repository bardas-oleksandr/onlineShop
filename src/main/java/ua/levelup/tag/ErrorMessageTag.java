package ua.levelup.tag;

import ua.levelup.exception.ApplicationException;
import ua.levelup.exception.ValidationException;
import ua.levelup.exception.support.MessageHolder;
import ua.levelup.model.User;
import ua.levelup.util.ResourceBundleHolder;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ErrorMessageTag extends TagSupport {

    private static final Logger logger = LogManager.getLogger();

    private static Properties properties = MessageHolder.getMessageProperties();
    private static final String WRONG_PASSWORD = properties.getProperty("WRONG_PASSWORD");
    private static final String EMPTY_USER_NAME = properties.getProperty("EMPTY_USER_NAME");
    private static final String EMPTY_EMAIL = properties.getProperty("EMPTY_EMAIL");
    private static final String EMPTY_PASSWORD = properties.getProperty("EMPTY_PASSWORD");
    private static final String LONG_USER_NAME = properties.getProperty("LONG_USER_NAME");
    private static final String LONG_EMAIL = properties.getProperty("LONG_EMAIL");
    private static final String LONG_PASSWORD = properties.getProperty("LONG_PASSWORD");
    private static final String INVALID_EMAIL_FORMAT = properties.getProperty("INVALID_EMAIL_FORMAT");
    private static final String EMPTY_ADDRESS = properties.getProperty("EMPTY_ADDRESS");
    private static final String UNACCEPTABLE_PAYMENT_CONDITIONS = properties.getProperty("UNACCEPTABLE_PAYMENT_CONDITIONS");
    private static final String NO_ORDER_POSITIONS = properties.getProperty("NO_ORDER_POSITIONS");
    private static final String EMPTY_PRODUCT_NAME = properties.getProperty("EMPTY_PRODUCT_NAME");
    private static final String UNACCEPTABLE_CATEGORY_ID = properties.getProperty("UNACCEPTABLE_CATEGORY_ID");
    private static final String UNACCEPTABLE_MANUFACTURER_ID = properties.getProperty("UNACCEPTABLE_MANUFACTURER_ID");
    private static final String UNACCEPTABLE_PRICE = properties.getProperty("UNACCEPTABLE_PRICE");
    private static final String NOT_UNIQUE_USER = properties.getProperty("NOT_UNIQUE_USER");
    private static final String EMPTY_RESULTSET = properties.getProperty("EMPTY_RESULTSET");
    private static final String FAILED_CHANGE_LANGUAGE = properties.getProperty("FAILED_CHANGE_LANGUAGE");
    private static final String NOT_UNIQUE_PRODUCT = properties.getProperty("NOT_UNIQUE_PRODUCT");
    private static final String EMPTY_CATEGORY_NAME = properties.getProperty("EMPTY_CATEGORY_NAME");
    private static final String UNACCEPTABLE_PARENT_ID = properties.getProperty("UNACCEPTABLE_PARENT_ID");
    private static final String EMPTY_MANUFACTURER_NAME = properties.getProperty("EMPTY_MANUFACTURER_NAME");
    private static final String NOT_UNIQUE_CATEGORY = properties.getProperty("NOT_UNIQUE_CATEGORY");
    private static final String NOT_UNIQUE_MANUFACTURER = properties.getProperty("NOT_UNIQUE_MANUFACTURER");
    private static final String FAILED_INSERT_PRODUCT = properties.getProperty("FAILED_INSERT_PRODUCT");
    private static final String FAILED_INSERT_CATEGORY = properties.getProperty("FAILED_INSERT_CATEGORY");
    private static final String FAILED_INSERT_MANUFACTURER = properties.getProperty("FAILED_INSERT_MANUFACTURER");

    private Exception exception;
    private Locale locale;
    private ResourceBundle resourceBundle;

    @Override
    public int doStartTag() throws JspException {
        logger.error("Message: " + exception.getMessage() + "\tCause: " + exception.getCause());
        exception.printStackTrace();
        try {
            if(locale == null){
                locale = Locale.US;
            }
            resourceBundle = ResourceBundleHolder.getBundle(locale);
            String errorMessage;
            if (exception != null) {
                String message = exception.getMessage();
                if (exception.getClass() == ValidationException.class) {
                    errorMessage = getMessageOnValidationException(message);
                } else if (exception.getClass() == ApplicationException.class) {
                    errorMessage = getMessageOnApplicationException(message);
                } else {
                    errorMessage = resourceBundle.getString("unknown_error");
                }
            } else {
                errorMessage = resourceBundle.getString("unknown_error");
            }
            pageContext.getOut().write(errorMessage);
        } catch (MissingResourceException e) {
            try {
                pageContext.getOut().write("Some problems with localization have occurred");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public void release() {
        exception = null;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    private String getMessageOnValidationException(String message) {
        String errorMessage;
        //Can't use switch here. Constant value is needed
        if (message.equals(WRONG_PASSWORD)) {
            errorMessage = resourceBundle.getString("wrong_password");
        } else if (message.contains(EMPTY_ADDRESS)) {
            errorMessage = resourceBundle.getString("empty_address");
        }else if (message.contains(UNACCEPTABLE_PAYMENT_CONDITIONS)) {
            errorMessage = resourceBundle.getString("unacceptable_payment_conditions");
        }else if (message.contains(NO_ORDER_POSITIONS)) {
            errorMessage = resourceBundle.getString("no_order_positions");
        }else if (message.contains(EMPTY_PRODUCT_NAME)) {
            errorMessage = resourceBundle.getString("empty_product_name");
        }else if (message.contains(UNACCEPTABLE_CATEGORY_ID)) {
            errorMessage = resourceBundle.getString("unacceptable_category_id");
        }else if (message.contains(UNACCEPTABLE_MANUFACTURER_ID)) {
            errorMessage = resourceBundle.getString("unacceptable_manufacturer_id");
        }else if (message.contains(UNACCEPTABLE_PRICE)) {
            errorMessage = resourceBundle.getString("unacceptable_price");
        }else if (message.contains(EMPTY_CATEGORY_NAME)) {
            errorMessage = resourceBundle.getString("empty_category_name");
        }else if (message.contains(UNACCEPTABLE_PARENT_ID)) {
            errorMessage = resourceBundle.getString("unacceptable_parent_id");
        }else if (message.contains(EMPTY_MANUFACTURER_NAME)) {
            errorMessage = resourceBundle.getString("empty_manufacturer_name");
        }else {
            errorMessage = resourceBundle.getString("invalid_credentials");
            if (message.equals(EMPTY_USER_NAME)) {
                errorMessage = errorMessage + resourceBundle.getString("empty_user_name");
            } else if (message.equals(EMPTY_EMAIL)) {
                errorMessage = errorMessage + resourceBundle.getString("empty_email");
            } else if (message.equals(EMPTY_PASSWORD)) {
                errorMessage = errorMessage + resourceBundle.getString("empty_password");
            } else if (message.contains(LONG_USER_NAME)) {
                errorMessage = errorMessage + resourceBundle.getString("long_user_name");
            } else if (message.contains(LONG_EMAIL)) {
                errorMessage = errorMessage + resourceBundle.getString("long_email");
            } else if (message.contains(LONG_PASSWORD)) {
                errorMessage = errorMessage + resourceBundle.getString("long_password");
            } else if (message.contains(INVALID_EMAIL_FORMAT)) {
                errorMessage = errorMessage + resourceBundle.getString("invalid_email_format");
                errorMessage = errorMessage + extractEmail(message);
            }
        }
        return errorMessage;
    }

    private String getMessageOnApplicationException(String message) {
        if (NOT_UNIQUE_USER.equals(message)) {
            return resourceBundle.getString("not_unique_user");
        } else if ((EMPTY_RESULTSET + User.class).equals(message)) {
            return resourceBundle.getString("user_does_not_exist");
        } else if (FAILED_CHANGE_LANGUAGE.equals(message)){
            return resourceBundle.getString("failed_change_language");
        }else if (NOT_UNIQUE_PRODUCT.equals(message)){
            return resourceBundle.getString("product_not_unique");
        } else if (NOT_UNIQUE_CATEGORY.equals(message)){
            return resourceBundle.getString("category_not_unique");
        }else if (NOT_UNIQUE_MANUFACTURER.equals(message)){
            return resourceBundle.getString("manufacturer_not_unique");
        }else if (FAILED_INSERT_PRODUCT.equals(message)){
            return resourceBundle.getString("failed_insert_product");
        }else if (FAILED_INSERT_CATEGORY.equals(message)){
            return resourceBundle.getString("failed_insert_category");
        }else if (FAILED_INSERT_MANUFACTURER.equals(message)){
            return resourceBundle.getString("failed_insert_manufacturer");
        }else{
            return resourceBundle.getString("nothing_have_to_do_with_problem");
        }
    }

    private String extractEmail(String message) {
        Pattern pattern = Pattern.compile("(" + INVALID_EMAIL_FORMAT + ")(.+)");
        Matcher matcher = pattern.matcher(message);
        if (matcher.find()) {
            return matcher.group(2);
        }
        throw new IllegalArgumentException("Invalid message format");
    }
}