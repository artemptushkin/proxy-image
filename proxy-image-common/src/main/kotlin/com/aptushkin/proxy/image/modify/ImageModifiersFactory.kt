package com.aptushkin.proxy.image.modify

import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

@Component
class ImageModifiersFactory {

    fun imageSizeModifier(width: Int, height: Int, format: String): ImageModifier = ImageSizeModifier(width, height, format)

    fun imageContentTypeModifierPredicate(responseHeaderName: String, regexp: String, onNotExistedHeader: Boolean):
            ImageModifierPredicate {
        return object: ImageModifierPredicate {
            override fun test(exchange: ServerWebExchange): Boolean {
                val values = (exchange.response.headers).getOrDefault(responseHeaderName, emptyList<String>())
                if (values.isEmpty()) return onNotExistedHeader
                return values
                            .stream()
                            .anyMatch { value -> value.matches(regexp.toRegex()) }
            }
        }
    }
}
