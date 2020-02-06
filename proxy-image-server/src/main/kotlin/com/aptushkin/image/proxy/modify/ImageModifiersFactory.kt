package com.aptushkin.image.proxy.modify

import org.springframework.stereotype.Component

@Component
class ImageModifiersFactory {

    fun imageSizeModifier(width: Int, height: Int, format: String): ImageModifier = ImageSizeModifier(width, height, format)
}
