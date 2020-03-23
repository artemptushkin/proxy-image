package com.aptushkin.proxy.image.modify.factory

import com.aptushkin.proxy.image.modify.ByteArrayModifier
import com.aptushkin.proxy.image.modify.CropImageModifier
import com.aptushkin.proxy.image.modify.ImageModifierPredicate
import com.aptushkin.proxy.image.modify.config.CropImageConfig
import com.aptushkin.proxy.image.modify.conversion.CropRequestConverter
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

@Component
class CropModifierFactory : ImageModifierFactory<CropImageConfig> {

    override fun imageModifier(config: CropImageConfig, serverWebExchange: ServerWebExchange): ByteArrayModifier {
        return CropImageModifier(
                CropRequestConverter(config).convert(serverWebExchange)
        )
    }

    override fun imageModifierPredicate(config: CropImageConfig, serverWebExchange: ServerWebExchange): ImageModifierPredicate {
        return object : ImageModifierPredicate {
            override fun test(exchange: ServerWebExchange): Boolean {
                val values = (exchange.response.headers).getOrDefault(config.responseHeaderName, emptyList<String>())
                if (values.isEmpty()) return config.onNotExistedHeader
                val params = serverWebExchange.request.queryParams
                return params.isNotEmpty() && values
                        .stream()
                        .anyMatch { value -> value.matches(config.regexp.toRegex()) }
            }
        }
    }
}
