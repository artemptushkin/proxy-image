package com.aptushkin.image.proxy.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils.CLIENT_RESPONSE_ATTR
import org.springframework.core.Ordered
import org.springframework.core.io.buffer.DataBuffer
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.client.ClientResponse
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

class ImageResponseFilter: GatewayFilter, Ordered {
    override fun getOrder(): Int = 0

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        return Mono.defer {
            val clientResponse = exchange
                    .getAttribute<ClientResponse>(CLIENT_RESPONSE_ATTR)
            val response = exchange.response

            response
                    .writeWith(
                            clientResponse!!.body<Flux<DataBuffer>>(BodyExtractors.toDataBuffers()))
        }
    }
}
