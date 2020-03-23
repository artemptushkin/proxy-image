package com.aptushkin.proxy.image.modify

import com.aptushkin.proxy.image.modify.domain.RotateImageRequest
import org.imgscalr.Scalr
import java.awt.image.BufferedImage

class RotateImageModifier(private val request: RotateImageRequest) : AbstractImageModifier(request.format){

    override fun modifyImage(image: BufferedImage): BufferedImage {
        return Scalr.rotate(image, request.rotation)
    }
}
