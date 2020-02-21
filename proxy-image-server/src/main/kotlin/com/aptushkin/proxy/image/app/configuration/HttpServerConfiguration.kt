package com.aptushkin.proxy.image.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.server.WebServer
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.HttpHandler
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Configuration
@ConditionalOnProperty(name = ["server.http.port"], matchIfMissing = false)
class HttpServerConfiguration {
    @Autowired
    private lateinit var httpHandler: HttpHandler

    lateinit var http: WebServer
    @Value("\${server.http.port}")
    private val port: Int = 8080

    @PostConstruct
    fun start() {
        val factory = NettyReactiveWebServerFactory(port)
        this.http = factory.getWebServer(this.httpHandler)
        this.http.start()
    }

    @PreDestroy
    fun stop() {
        this.http.stop()
    }
}
