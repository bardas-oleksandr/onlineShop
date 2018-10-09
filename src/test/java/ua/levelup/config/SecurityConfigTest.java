//package ua.levelup.config;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.Set;
//
//public class SecurityConfigTest {
//    @Test
//    public void getProtectedURLs() throws Exception {
//        //WHEN
//        Set<String> protectedUrls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.ALL);
//        Set<String> protectedFromAdminUrls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.ADMIN);
//        Set<String> protectedFromActiveUrls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.ACTIVE);
//        Set<String> protectedFromBlockedUrls = SecurityConfig.getProtectedURLs(SecurityConfig.ProtectedUrlType.BLOCKED);
//        //THEN
//        Assert.assertNotNull(protectedUrls);
//        Assert.assertNotNull(protectedFromAdminUrls);
//        Assert.assertNotNull(protectedFromActiveUrls);
//        Assert.assertNotNull(protectedFromBlockedUrls);
//    }
//}