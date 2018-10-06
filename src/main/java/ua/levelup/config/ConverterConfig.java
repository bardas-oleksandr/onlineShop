package ua.levelup.config;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Setter
@Configuration
public class ConverterConfig {

    @Autowired
    private Set<Converter<?,?>> converterSet;

    @Bean
    ConversionServiceFactoryBean conversionServiceFactoryBean(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(converterSet);
        return new ConversionServiceFactoryBean();
    }
}
