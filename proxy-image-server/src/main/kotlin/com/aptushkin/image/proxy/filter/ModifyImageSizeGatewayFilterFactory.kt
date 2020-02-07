package com.aptushkin.image.proxy.filter

import com.aptushkin.image.proxy.modify.ImageModifiersFactory
import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.RewriteFunction
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono
import javax.validation.constraints.NotEmpty

@Component
class ModifyImageSizeGatewayFilterFactory(private val decoratedFactory: ModifyResponseBodyGatewayFilterFactory,
                                          private val imageModifiersFactory: ImageModifiersFactory)
    : AbstractGatewayFilterFactory<ModifyImageSizeGatewayFilterFactory.Config>(Config::class.java) {

    override fun apply(config: Config): GatewayFilter {
        return decoratedFactory.apply(config.apply {
            inClass = ByteArray::class.java
            outClass = ByteArray::class.java
            rewriteFunction = RewriteFunction { exchange: ServerWebExchange, byteArray: ByteArray ->
                val uri = exchange.request.uri.toString()
                val params = exchange.request.queryParams
                val predicateSucceeded = imageModifiersFactory
                        .imageContentTypeModifierPredicate(config.responseHeaderName, config.regexp, onNotExistedHeader)
                        .test(exchange)
                if (predicateSucceeded && params.isNotEmpty()) {
                    val format = uri.substring(uri.lastIndexOf(".") + 1, uri.lastIndexOf("?"))
                    Mono.just(
                            imageModifiersFactory.imageSizeModifier(
                                    params["width"]!!.firstOrNull()!!.toInt(), params["height"]!!.firstOrNull()!!.toInt(), format
                            ).modify(byteArray)
                    )
                } else {
                    Mono.just(byteArray)
                }
            }
        })
    }

    @Validated
    class Config: ModifyResponseBodyGatewayFilterFactory.Config() {
        @NotEmpty
        lateinit var responseHeaderName: String
        @NotEmpty
        lateinit var regexp: String
        var onNotExistedHeader = false
    }
}
