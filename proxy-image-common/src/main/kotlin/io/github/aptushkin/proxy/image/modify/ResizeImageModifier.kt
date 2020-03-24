package io.github.aptushkin.proxy.image.modify

import io.github.aptushkin.proxy.image.modify.domain.ResizeRequest
import org.imgscalr.Scalr
import java.awt.image.BufferedImage

class ResizeImageModifier(private val resizeRequest: ResizeRequest): AbstractImageModifier(resizeRequest.format) {

    override fun modifyImage(image: BufferedImage): BufferedImage {
        val method = resizeRequest.method
        val mode = resizeRequest.mode
        val width = resizeRequest.width
        val height = resizeRequest.height
        if (method != null) {
            if (mode != null) return Scalr.resize(image, method, mode, width, height)
            return Scalr.resize(image, method, width, height)
        }
        if (mode != null) Scalr.resize(image, mode, width, height)
        return Scalr.resize(image, width, height)
    }
}
