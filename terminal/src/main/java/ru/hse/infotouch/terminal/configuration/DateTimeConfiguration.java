package ru.hse.infotouch.terminal.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.datetime.standard.DateTimeFormatterRegistrar;
import org.springframework.format.support.DefaultFormattingConversionService;
import org.springframework.format.support.FormattingConversionService;

import java.time.format.DateTimeFormatter;

@Configuration
public class DateTimeConfiguration {

//    @Bean
    public FormattingConversionService formattingConversionService() {
        DefaultFormattingConversionService conversionService =
                new DefaultFormattingConversionService(false);


        DateTimeFormatterRegistrar registrar = new DateTimeFormatterRegistrar();
        registrar.setDateFormatter(DateTimeFormatter.ofPattern("yyyy.MM.dd"));
        registrar.setDateTimeFormatter(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:ss"));
        registrar.setTimeFormatter(DateTimeFormatter.ofPattern("HH:ss"));

        registrar.registerFormatters(conversionService);

        return conversionService;
    }
}
