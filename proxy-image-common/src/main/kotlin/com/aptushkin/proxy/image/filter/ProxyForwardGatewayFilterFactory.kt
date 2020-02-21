package com.aptushkin.proxy.image.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.stereotype.Component

@Component
class ProxyForwardGatewayFilterFactory:
        AbstractGatewayFilterFactory<AbstractGatewayFilterFactory.NameConfig>(NameConfig::class.java) {

    override fun apply(config: NameConfig): GatewayFilter {
        return ProxyForwardGatewayFilter()
    }
}
