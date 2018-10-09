package ua.levelup.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;
import org.springframework.core.convert.converter.Converter;

import java.util.Set;

@Configuration
public class ConverterConfig {

    private Set<Converter<?,?>> converterSet;

    @Bean
    ConversionServiceFactoryBean conversionServiceFactoryBean(){
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(converterSet);
        return new ConversionServiceFactoryBean();
    }

    @Autowired
    public void setConverterSet(@Value("#{converterSet}") Set<Converter<?,?>> converterSet){
        this.converterSet = converterSet;
    }
}