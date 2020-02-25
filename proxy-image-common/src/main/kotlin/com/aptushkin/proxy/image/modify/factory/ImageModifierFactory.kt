package com.aptushkin.proxy.image.modify.factory

import com.aptushkin.proxy.image.modify.ImageModifier
import com.aptushkin.proxy.image.modify.ImageModifierPredicate
import org.springframework.web.server.ServerWebExchange

interface ImageModifierFactory<in C> {
    fun imageModifier(config: C, serverWebExchange: ServerWebExchange): ImageModifier

    fun imageModifierPredicate(config: C, serverWebExchange: ServerWebExchange): ImageModifierPredicate
}
