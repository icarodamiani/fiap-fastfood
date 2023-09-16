package io.fiap.fastfood.infrastructure.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.config.BeanPostProcessor
import org.springframework.boot.autoconfigure.codec.CodecProperties
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.codec.ClientCodecConfigurer
import org.springframework.http.codec.json.Jackson2JsonDecoder
import org.springframework.http.codec.json.Jackson2JsonEncoder
import org.springframework.stereotype.Component
import org.springframework.util.unit.DataSize
import org.springframework.web.reactive.function.client.WebClient
import java.util.function.Consumer

/**
 * Configure reactive web client for use conventional serialization/deserialization Jackson configured module.
 */
@Configuration
class WebClientAutoConfiguration {
    @Component
    class ConfigureWebClientBeanPostProcessor internal constructor(
        val springContext: ConfigurableApplicationContext,
        val objectMapper: ObjectMapper,
        val codecProperties: CodecProperties
    ) : BeanPostProcessor {
        override fun postProcessBeforeInitialization(bean: Any, beanName: String): Any? {
            return bean
        }

        override fun postProcessAfterInitialization(bean: Any, beanName: String): Any? {
            if (bean is WebClient) {
                return wrapBuilder(bean.mutate()).build()
            } else if (bean is WebClient.Builder) {
                return wrapBuilder(bean)
            }
            return bean
        }

        private fun wrapBuilder(webClientBuilder: WebClient.Builder): WebClient.Builder {
            return webClientBuilder.codecs(configure())
        }

        private fun configure(): Consumer<ClientCodecConfigurer> {
            return Consumer { codecConfigurer: ClientCodecConfigurer ->
                configureCodecs().accept(codecConfigurer)
                configureMaxInMemory().accept(codecConfigurer)
            }
        }

        private fun configureCodecs(): Consumer<ClientCodecConfigurer> {
            return Consumer { codecConfigurer: ClientCodecConfigurer ->
                codecConfigurer.defaultCodecs().jackson2JsonEncoder(Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON))
                codecConfigurer.defaultCodecs().jackson2JsonDecoder(Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON))
            }
        }

        private fun configureMaxInMemory(): Consumer<ClientCodecConfigurer> {
            val maxMemory: DataSize = if (codecProperties != null && codecProperties.maxInMemorySize != null) {
                codecProperties.maxInMemorySize
            } else {
                DEFAULT_CODEC_MEMORY
            }
            return Consumer { configurer: ClientCodecConfigurer -> configurer.defaultCodecs().maxInMemorySize(maxMemory.toBytes().toInt()) }
        }

        companion object {
            private val DEFAULT_CODEC_MEMORY = DataSize.ofKilobytes(256)
        }
    }
}