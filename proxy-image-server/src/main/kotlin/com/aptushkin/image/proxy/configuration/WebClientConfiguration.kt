package com.aptushkin.image.proxy.configuration

import com.aptushkin.image.proxy.filter.TargetUriForwardGatewayFilter
import org.reactivestreams.Publisher
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.web.server.ServerWebExchange


@Configuration
class WebClientConfiguration {

    @Bean
    fun modifyResponseGatewayFilter(codecConfigurer: ServerCodecConfigurer): GatewayFilter {
        val config = ModifyResponseBodyGatewayFilterFactory.Config().apply {
            inClass = ByteArray::class.java
            outClass = ByteArray::class.java
            rewriteFunction = RewriteFunction { serverWebExchange: ServerWebExchange, byteArray: ByteArray ->
                println(byteArray)
                Publisher<ByteArray> {
                    it.onNext(byteArray)
                }
            }
        }
        return ModifyResponseBodyGatewayFilterFactory(codecConfigurer).apply(config)
    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder, codecConfigurer: ServerCodecConfigurer): RouteLocator {
        return builder.routes()
                .route { r ->
                    r
                            .alwaysTrue()
                            .filters { f ->
                                f
                                        .filter(TargetUriForwardGatewayFilter())
                                        .filter(this.modifyResponseGatewayFilter(codecConfigurer))
                            }
                            .uri("no://op")
                }
                .build()
    }
}
