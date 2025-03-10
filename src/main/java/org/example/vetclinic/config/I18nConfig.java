package org.example.vetclinic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * Configuration class for internationalization (i18n) settings.
 * This class configures the locale resolver and locale change interceptor,
 * allowing dynamic switching of the application's language based on user preferences.
 */
@Configuration
public class I18nConfig implements WebMvcConfigurer {

    /**
     * Defines a {@link LocaleResolver} bean that uses session-based locale resolution.
     * The default locale is set to English (UK).
     *
     * @return a {@link LocaleResolver} for session-based locale management.
     */
    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
        localeResolver.setDefaultLocale(new Locale("en", "UK"));
        return localeResolver;
    }

    /**
     * Defines a {@link LocaleChangeInterceptor} bean that allows the application's locale
     * to be changed based on a request parameter.
     * The "lang" parameter is used to specify the desired language.
     *
     * @return a {@link LocaleChangeInterceptor} to intercept requests and change the locale.
     */
    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    /**
     * Adds the {@link LocaleChangeInterceptor} to the application's interceptor registry.
     * This allows the locale to be changed dynamically by providing a "lang" parameter in the request.
     *
     * @param registry the interceptor registry to add the interceptor to.
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }


}
