package com.t1tanic.true_vision.config;

import com.t1tanic.true_vision.enums.i18n.CodeI18N;
import com.t1tanic.true_vision.enums.i18n.LocatorI18N;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Locale;

/**
 * Configuration class for Internationalization (i18n) settings.
 *
 * @author Gabriel Vendramini
 */
@Configuration
public class I18nConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

        // Usamos tus Enums para construir las rutas automáticamente
        String[] basenames = java.util.Arrays.stream(LocatorI18N.values())
                .map(locator -> "classpath:" + locator.getPath())
                .toArray(String[]::new);

        messageSource.setBasenames(basenames);
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        localeResolver.setDefaultLocale(new Locale(CodeI18N.ENGLISH.getCode()));
        return localeResolver;
    }
}
