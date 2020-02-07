package com.aptushkin.image.proxy.filter

import org.springframework.cloud.gateway.filter.GatewayFilter
import org.springframework.cloud.gateway.filter.NettyWriteResponseFilter
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory
import org.springframework.core.Ordered
import org.springframework.validation.annotation.Validated
import reactor.core.publisher.Mono
import javax.validation.constraints.NotEmpty

class ResponseHeaderGatewayFilterFactory:
        AbstractGatewayFilterFactory<ResponseHeaderGatewayFilterFactory.Config>(
                Config::class.java), Ordered {

    override fun getOrder(): Int = NettyWriteResponseFilter.WRITE_RESPONSE_FILTER_ORDER - 1

    override fun apply(config: Config): GatewayFilter {
        return GatewayFilter { exchange, chain ->
            val values = (exchange.response.headers).getOrDefault(config.responseHeaderName, emptyList<String>())
            if (values.isEmpty() && config.onNotExistedHeader) chain.filter(exchange)
            if (values
                            .stream()
                            .anyMatch { value -> value.matches(config.regexp.toRegex()) }) {
                chain.filter(exchange)
            }
            Mono.empty()
        }
    }

    @Validated
    class Config {
        @NotEmpty
        lateinit var responseHeaderName: String
        @NotEmpty
        lateinit var regexp: String
        var onNotExistedHeader = false
    }
}
