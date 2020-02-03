package com.aptushkin.image.proxy.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.GatewayFilterChain
import org.springframework.cloud.gateway.filter.RouteToRequestUrlFilter.ROUTE_TO_URL_FILTER_ORDER
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils
import org.springframework.core.Ordered
import org.springframework.web.server.ServerWebExchange
import reactor.core.publisher.Mono

class TargetUriForwardGatewayFilter: GatewayFilter, Ordered {
    override fun getOrder(): Int = ROUTE_TO_URL_FILTER_ORDER + 1

    override fun filter(exchange: ServerWebExchange, chain: GatewayFilterChain): Mono<Void> {
        val target = exchange.request.uri
        exchange.attributes[ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR] = target
        return chain.filter(exchange)
    }
}
