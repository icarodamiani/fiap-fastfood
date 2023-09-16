package io.fiap.fastfood.infrastructure.configuration

import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.InsecureTrustManagerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.netty.http.client.HttpClient

@Configuration
class WebClientConfiguration {


/*    @Qualifier("xpto-client")
    @Bean
    fun xptoClient(
        builder: WebClient.Builder,
        @Value("\${xpto.url}") endpoint: String,
    ): WebClient? {
        return getWebClient(builder, endpoint)
    }*/

    private fun getWebClient(
        builder: WebClient.Builder, endpoint: String
    ): WebClient {

        val sslContext = SslContextBuilder
            .forClient()
            .trustManager(InsecureTrustManagerFactory.INSTANCE)
            .build()

        val httpClient = HttpClient.create().secure { t -> t.sslContext(sslContext) }


        return builder
            .uriBuilderFactory(uriBuilderFactory(endpoint))
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .build()
    }

    private fun uriBuilderFactory(baseUri: String): DefaultUriBuilderFactory {
        return DefaultUriBuilderFactory(baseUri)
    }
}
