package com.aptushkin.image.proxy.configuration

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory
import org.springframework.boot.web.server.WebServer
import org.springframework.context.annotation.Configuration
import org.springframework.http.server.reactive.HttpHandler
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy


@Configuration
class HttpServerConfiguration {
    @Autowired
    var httpHandler: HttpHandler? = null

    lateinit var http: WebServer

    @PostConstruct
    fun start() {
        val factory = NettyReactiveWebServerFactory(8080)
        this.http = factory.getWebServer(this.httpHandler)
        this.http.start()
    }

    @PreDestroy
    fun stop() {
        this.http.stop()
    }
}
