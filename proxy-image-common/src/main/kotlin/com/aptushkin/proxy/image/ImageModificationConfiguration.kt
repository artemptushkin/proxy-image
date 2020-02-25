package com.aptushkin.proxy.image

import com.aptushkin.proxy.image.filter.ModifyImageGatewayFilterFactory
import com.aptushkin.proxy.image.modify.config.ResizeImageConfig
import com.aptushkin.proxy.image.modify.factory.ImageModifierFactory
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Component

@Configuration
@ComponentScan
class ImageModificationConfiguration {

    @Component
    class ModifyImageSizeGatewayFilterFactory(
            decoratedFactory: ModifyResponseBodyGatewayFilterFactory, imageModifierFactory: ImageModifierFactory<ResizeImageConfig>)
        : ModifyImageGatewayFilterFactory<ResizeImageConfig>(decoratedFactory, imageModifierFactory, ResizeImageConfig::class.java)
}
