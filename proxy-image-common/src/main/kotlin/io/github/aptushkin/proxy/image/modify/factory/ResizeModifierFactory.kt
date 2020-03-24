package io.github.aptushkin.proxy.image.modify.factory

import io.github.aptushkin.proxy.image.modify.ByteArrayModifier
import io.github.aptushkin.proxy.image.modify.ResizeImageModifier
import io.github.aptushkin.proxy.image.modify.config.ResizeImageConfig
import io.github.aptushkin.proxy.image.modify.conversion.ResizeRequestConverter
import io.github.aptushkin.proxy.image.modify.predicate.DefaultImageModifierPredicate
import io.github.aptushkin.proxy.image.modify.predicate.ImageModifierPredicate
import org.springframework.stereotype.Component
import org.springframework.web.server.ServerWebExchange

@Component
class ResizeModifierFactory : ImageModifierFactory<ResizeImageConfig> {

    override fun imageModifier(config: ResizeImageConfig, serverWebExchange: ServerWebExchange): ByteArrayModifier {
        return ResizeImageModifier(
                ResizeRequestConverter(config).convert(serverWebExchange)
        )
    }

    override fun imageModifierPredicate(config: ResizeImageConfig, serverWebExchange: ServerWebExchange): ImageModifierPredicate {
        return DefaultImageModifierPredicate(config)
    }
}
