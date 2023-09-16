package io.fiap.fastfood.infrastructure.configuration

import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.server.WebServer
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.ContextPathCompositeHandler
import org.springframework.http.server.reactive.HttpHandler
import java.util.*

@Configuration
class NettyWebServerConfiguration(@Value("\${SERVER_CONTEXT_PATH:/}") private val contextPath: String) :
    NettyReactiveWebServerFactory() {
    override fun getWebServer(httpHandler: HttpHandler): WebServer {
        val handlerMap: MutableMap<String, HttpHandler> = HashMap()
        handlerMap.put(contextPath, httpHandler)
        LOGGER.info("Configuring context path to {}", contextPath)
        return super.getWebServer(ContextPathCompositeHandler(handlerMap))
    }

    companion object {
        private val LOGGER = LoggerFactory.getLogger(NettyWebServerConfiguration::class.java)
    }
}