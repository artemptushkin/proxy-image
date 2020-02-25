package com.aptushkin.proxy.image.modify.factory

import com.aptushkin.proxy.image.modify.ImageModifier
import com.aptushkin.proxy.image.modify.ImageModifierPredicate
import com.aptushkin.proxy.image.modify.ResizeModifier
import com.aptushkin.proxy.image.modify.config.ResizeImageConfig
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

@Component
class ResizeModifierFactory: ImageModifierFactory<ResizeImageConfig> {

    override fun imageModifier(config: ResizeImageConfig, serverWebExchange: ServerWebExchange): ImageModifier {
        val uri = serverWebExchange.request.uri.toString()
        val params = serverWebExchange.request.queryParams
        val format = uri.substring(uri.lastIndexOf(".") + 1, uri.lastIndexOf("?"))

        return ResizeModifier(params["width"]!!.firstOrNull()!!.toInt(), params["height"]!!.firstOrNull()!!.toInt(), format)
    }

    override fun imageModifierPredicate(config: ResizeImageConfig, serverWebExchange: ServerWebExchange): ImageModifierPredicate {
        return object: ImageModifierPredicate {
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
