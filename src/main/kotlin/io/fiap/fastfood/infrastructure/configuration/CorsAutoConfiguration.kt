package io.fiap.fastfood.infrastructure.configuration

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.reactive.CorsWebFilter
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource
import org.springframework.web.util.pattern.PathPatternParser

/**
 * Disable CORS configuration completely. Projects may override this definition
 * providing a custom instance of bean [CorsWebFilter].
 */
@Configuration
class CorsAutoConfiguration {
    /**
     * Provide default [CorsWebFilter] instance.
     */
    @Bean
    @ConditionalOnMissingBean(CorsWebFilter::class)
    fun corsFilter(): CorsWebFilter {
        val config = CorsConfiguration()
        config.allowCredentials = true
        config.addAllowedOrigin("*")
        config.addAllowedHeader("*")
        config.addAllowedMethod("*")
        val source = UrlBasedCorsConfigurationSource(PathPatternParser())
        source.registerCorsConfiguration("/**", config)
        return CorsWebFilter(source)
    }
}