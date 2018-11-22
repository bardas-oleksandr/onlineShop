package ua.levelup.controller.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Properties;

@Component("messageResolver")
public class MessageResolver {

    @Autowired
    private Properties messagesProperties;

    public String resolveMessageForException(Exception e) {
        String message = e.getMessage();
        if (message.equals(messagesProperties.getProperty("NOT_UNIQUE_USER"))) {
            return "not_unique_user";
        } else if (message.equals(messagesProperties.getProperty("NOT_UNIQUE_PRODUCT"))) {
            return "not_unique_product";
        } else if (message.equals(messagesProperties.getProperty("NOT_UNIQUE_CATEGORY"))) {
            return "not_unique_category";
        } else if (message.equals(messagesProperties.getProperty("NOT_UNIQUE_MANUFACTURER"))) {
            return "not_unique_manufacturer";
        }
        return "you_should_watch_logs";
    }
}