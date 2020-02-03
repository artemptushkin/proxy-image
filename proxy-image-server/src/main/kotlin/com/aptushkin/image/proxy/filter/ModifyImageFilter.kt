package com.aptushkin.image.proxy.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.http.codec.ServerCodecConfigurer
import java.util.function.Consumer

class MyModifyResponseBodyGatewayFilterFactory(private val serverCodecConfigurer: ServerCodecConfigurer):
        ModifyResponseBodyGatewayFilterFactory(serverCodecConfigurer) {
    override fun apply(routeId: String?, consumer: Consumer<Config>?): GatewayFilter {
        return super.apply(routeId, consumer)
    }
}
