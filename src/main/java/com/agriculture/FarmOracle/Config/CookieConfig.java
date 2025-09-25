package com.agriculture.FarmOracle.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.web.http.DefaultCookieSerializer;
import org.springframework.session.web.http.CookieSerializer;


@Configuration
public class CookieConfig {

    @Bean
    public CookieSerializer cookieSerializer() {
        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
        
        // Set SameSite to None to allow cookies in cross-origin requests
        cookieSerializer.setSameSite("None");  // This is necessary for cross-site authentication
        cookieSerializer.setUseSecureCookie(true);  // Secure cookies for HTTPS (recommended for production)

        // You can also specify other settings like cookie name or path if needed
        cookieSerializer.setCookieName("JSESSIONID"); // Optional: Set a custom cookie name if required
        cookieSerializer.setCookiePath("/");  // Set the path for the cookie ("/" for the entire domain)

        return cookieSerializer;
    }
}
