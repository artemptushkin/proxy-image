package com.aptushkin.proxy.image.modify

import com.aptushkin.proxy.image.modify.AbstractImageModifier
import com.aptushkin.proxy.image.modify.domain.CropRequest
import org.imgscalr.Scalr
import java.awt.image.BufferedImage

class CropImageModifier(private val cropRequest: CropRequest): AbstractImageModifier(cropRequest.format) {
    override fun modifyImage(image: BufferedImage): BufferedImage {
        val width = cropRequest.width
        val height = cropRequest.height
        val x = cropRequest.x
        val y = cropRequest.y
        if (x != null && y != null) {
            return Scalr.crop(image, x, y, width, height)
        }
        return Scalr.crop(image, width, height);
    }
}