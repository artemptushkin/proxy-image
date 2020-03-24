package io.github.aptushkin.proxy.image.modify.factory

import io.github.aptushkin.proxy.image.modify.ByteArrayModifier
import io.github.aptushkin.proxy.image.modify.CropImageModifier
import io.github.aptushkin.proxy.image.modify.config.CropImageConfig
import io.github.aptushkin.proxy.image.modify.conversion.CropRequestConverter
import io.github.aptushkin.proxy.image.modify.predicate.DefaultImageModifierPredicate
import io.github.aptushkin.proxy.image.modify.predicate.ImageModifierPredicate
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
        return DefaultImageModifierPredicate(config)
    }
}
