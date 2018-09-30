package ua.levelup.util;

import java.util.Locale;
import java.util.ResourceBundle;

public enum ResourceBundleHolder {
    ;

    private static Utf8Control utf8Control = new Utf8Control();

    public static ResourceBundle getBundle(Locale locale) {
        return ResourceBundle.getBundle("i18n.messages", locale, utf8Control);
    }

    public static ResourceBundle getBundleByLanguage(String language) {
        Locale locale = LocalizationSupport.getLocale(language);
        return ResourceBundle.getBundle("i18n.messages", locale, utf8Control);
    }
}
