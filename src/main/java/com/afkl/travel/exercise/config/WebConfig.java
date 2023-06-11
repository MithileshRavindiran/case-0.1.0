package com.afkl.travel.exercise.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private final LocationTypeConverter locationTypeConverter;

    public WebConfig(LocationTypeConverter locationTypeConverter) {
        this.locationTypeConverter = locationTypeConverter;
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(locationTypeConverter);
    }
}
