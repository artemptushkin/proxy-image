package com.aptushkin.proxy.image.filter

import com.aptushkin.proxy.image.modify.factory.ImageModifierFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

open class ModifyImageGatewayFilterFactory<C : ModifyResponseBodyGatewayFilterFactory.Config>(
        private val decoratedFactory: ModifyResponseBodyGatewayFilterFactory,
        private val imageModifierFactory: ImageModifierFactory<C>,
        configType: Class<C>
) : AbstractGatewayFilterFactory<C>(configType) {

    override fun apply(config: C): GatewayFilter {
        return decoratedFactory.apply(config.apply {
            inClass = ByteArray::class.java
            outClass = ByteArray::class.java
            rewriteFunction = RewriteFunction { exchange: ServerWebExchange, byteArray: ByteArray ->
                val predicateSucceeded = imageModifierFactory
                        .imageModifierPredicate(config, exchange)
                        .test(exchange)
                if (predicateSucceeded) {
                    Mono.just(imageModifierFactory.imageModifier(config, exchange).modify(byteArray))
                } else {
                    Mono.just(byteArray)
                }
            }
        })
    }
}
