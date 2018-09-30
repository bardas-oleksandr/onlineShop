package ua.levelup.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.Locale;
import java.util.Optional;

public enum LocalizationSupport {
    ;

    private static final Logger logger = LogManager.getLogger();
    private static final int MAX_AGE = 60*60*24;

    public static Locale getLocale(String language) {
        if (language == null) {
            logger.warn("Request for locale of unsupported language: " + language);
            return Locale.US;
        }
        switch (language) {
            case "en":
                return Locale.US;
            case "ru":
                return new Locale.Builder().setLanguage("ru").setRegion("UA").build();
            default:
                logger.warn("Request for locale of unsupported language: " + language);
                return Locale.US;
        }
    }

    public static Locale switchLocale(Locale locale){
        if(locale == null || locale.getLanguage() == null){
            return getLocale("en");
        }
        switch(locale.getLanguage()){
            case "en": return getLocale("ru");
            case "ru": return getLocale("en");
            default: return getLocale("en");
        }
    }

    public static void createAndAddCookieToResponse(String cookieName, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(cookieName, value);
        addCookie(cookie,response);
    }

    public static void addCookie(Cookie cookie, HttpServletResponse response) {
        cookie.setMaxAge(MAX_AGE);
        response.addCookie(cookie);
    }

    public static Optional<Cookie> extractCookieFromRequest(HttpServletRequest httpServletRequest, String cookieName) {
        HttpSession session = httpServletRequest.getSession(true); //Session object is not actually used, but when
        //method getSession(true) is executed, Cookie JSESSIONID is created
        Optional<Cookie[]> cookies = Optional.ofNullable(httpServletRequest.getCookies());
        return findCookieByName(cookies, cookieName);
    }

    private static Optional<Cookie> findCookieByName(Optional<Cookie[]> cookies, String cookieName) {
        return Arrays.stream(cookies.orElse(new Cookie[0]))
                .filter((item) -> item.getName().equals(cookieName))
                .findAny();
    }
}
