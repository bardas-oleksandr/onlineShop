package ua.levelup.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

@Configuration
@ComponentScan("ua.levelup.converter.*")
@ImportResource({"classpath:context/app-context.xml"})
public class TestContextConfig {

}