package io.umehara.lunchFinderServer

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
@EnableWebMvc
class WebConfig: WebMvcConfigurerAdapter() {

    override fun addCorsMappings(registry: CorsRegistry?) {
        if (registry != null) {
            registry.addMapping("/**")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedOrigins(System.getenv("CLIENT_URL"))
        }
    }
}