package com.aptushkin.image.proxy.configuration

import com.aptushkin.image.proxy.filter.TargetUriForwardGatewayFilter
import com.aptushkin.image.proxy.modify.ImageModifiersFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction
import org.springframework.cloud.gateway.route.RouteLocator
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono


@Configuration
class WebClientConfiguration {

    @Bean
    fun config(imageModifiersFactory: ImageModifiersFactory):
            ModifyResponseBodyGatewayFilterFactory.Config {
        return ModifyResponseBodyGatewayFilterFactory.Config().apply {
            inClass = ByteArray::class.java
            outClass = ByteArray::class.java
            rewriteFunction = RewriteFunction { exchange: ServerWebExchange, byteArray: ByteArray ->
                val uri = exchange.request.uri.toString()
                val params = exchange.request.queryParams
                    val format = uri.substring(uri.lastIndexOf(".") + 1, uri.lastIndexOf("?"))
                    Mono.just(
                            imageModifiersFactory.imageSizeModifier(
                                    params["width"]!!.firstOrNull()!!.toInt(), params["height"]!!.firstOrNull()!!.toInt(), format
                            ).modify(byteArray)
                    )
            }
        }
    }

    @Bean
    fun routeLocator(builder: RouteLocatorBuilder,
                     modifyResponseBodyGatewayFilterFactory: ModifyResponseBodyGatewayFilterFactory,
                     imageModifiersFactory: ImageModifiersFactory):
            RouteLocator {
        return builder.routes()
                .route { r ->
                    r
                            .alwaysTrue()
                            .filters { f ->
                                f
                                        .filter(TargetUriForwardGatewayFilter())
                                        .filter(modifyResponseBodyGatewayFilterFactory.apply(config(imageModifiersFactory)))
                            }
                            .uri("no://op")
                }
                .build()
    }
}
