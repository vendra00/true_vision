package com.t1tanic.true_vision.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    /**
     * Resolves a key against the current Locale.
     * * @param key  The message key in the properties file.
     * @param args Optional arguments for the message (placeholders like {0}).
     * @return The localized message or the key itself if resolution fails.
     */
    public String resolve(String key, Object... args) {
        // Automatically gets the locale from the thread-local storage (Accept-Language header)
        Locale locale = LocaleContextHolder.getLocale();
        try {
            return messageSource.getMessage(key, args, locale);
        } catch (Exception e) {
            log.error("Could not resolve i18n key: '{}' for locale: '{}'", key, locale);
            return key; // Fallback to the key name if resolution fails
        }
    }
}
