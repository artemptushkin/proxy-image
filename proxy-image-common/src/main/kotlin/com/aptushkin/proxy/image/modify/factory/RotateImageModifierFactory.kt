package com.aptushkin.proxy.image.modify.factory

import com.aptushkin.proxy.image.modify.ByteArrayModifier
import com.aptushkin.proxy.image.modify.RotateImageModifier
import com.aptushkin.proxy.image.modify.config.RotateImageConfig
import com.aptushkin.proxy.image.modify.conversion.RotateImageConverter
import com.aptushkin.proxy.image.modify.predicate.DefaultImageModifierPredicate
import com.aptushkin.proxy.image.modify.predicate.ImageModifierPredicate
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

@Component
class RotateImageModifierFactory: ImageModifierFactory<RotateImageConfig> {
    override fun imageModifier(config: RotateImageConfig, serverWebExchange: ServerWebExchange): ByteArrayModifier {
        return RotateImageModifier(
                RotateImageConverter(config).convert(serverWebExchange)
        )
    }

    override fun imageModifierPredicate(config: RotateImageConfig, serverWebExchange: ServerWebExchange): ImageModifierPredicate = DefaultImageModifierPredicate(config)
}