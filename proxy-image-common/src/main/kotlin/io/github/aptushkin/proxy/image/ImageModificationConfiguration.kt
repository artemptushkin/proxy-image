package io.github.aptushkin.proxy.image

import io.github.aptushkin.proxy.image.filter.ModifyImageGatewayFilterFactory
import io.github.aptushkin.proxy.image.modify.config.CropImageConfig
import io.github.aptushkin.proxy.image.modify.config.ResizeImageConfig
import io.github.aptushkin.proxy.image.modify.config.RotateImageConfig
import io.github.aptushkin.proxy.image.modify.factory.ImageModifierFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@ComponentScan
class ImageModificationConfiguration {

    @Component
    class ResizeImageGatewayFilterFactory(
            decoratedFactory: ModifyResponseBodyGatewayFilterFactory, resizeModifierFactory: ImageModifierFactory<ResizeImageConfig>)
        : ModifyImageGatewayFilterFactory<ResizeImageConfig>(decoratedFactory, resizeModifierFactory, ResizeImageConfig::class.java)

    @Component
    class CropImageGatewayFilterFactory(
            decoratedFactory: ModifyResponseBodyGatewayFilterFactory, cropModifierFactory: ImageModifierFactory<CropImageConfig>)
        : ModifyImageGatewayFilterFactory<CropImageConfig>(decoratedFactory, cropModifierFactory, CropImageConfig::class.java)

    @Component
    class RotateImageGatewayFilterFactory(
            decoratedFactory: ModifyResponseBodyGatewayFilterFactory, rotateModifierFactory: ImageModifierFactory<RotateImageConfig>)
        : ModifyImageGatewayFilterFactory<RotateImageConfig>(decoratedFactory, rotateModifierFactory, RotateImageConfig::class.java)
}
