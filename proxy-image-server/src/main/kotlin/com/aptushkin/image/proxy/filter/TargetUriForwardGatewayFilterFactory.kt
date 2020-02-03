package com.aptushkin.image.proxy.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.AbstractNameValueGatewayFilterFactory
import org.springframework.stereotype.Component

@Component
class TargetUriForwardGatewayFilterFactory: AbstractNameValueGatewayFilterFactory() {

    override fun apply(config: NameValueConfig): GatewayFilter {
        return TargetUriForwardGatewayFilter()
    }
}
