package com.aptushkin.proxy.image.modify

import org.imgscalr.Scalr
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

class ImageSizeModifier(private val width: Int, private val height: Int, private val format: String) : ImageModifier {

    override fun modify(byteArray: ByteArray): ByteArray {
        val inputImage = ByteArrayInputStream(byteArray).use {
            return@use ImageIO.read(it)
        }

        val scaledImage: BufferedImage = Scalr.resize(inputImage, Scalr.Method.ULTRA_QUALITY, Scalr.Mode.FIT_EXACT, width, height)

        return ByteArrayOutputStream().use {
            ImageIO.write(scaledImage, format, it)
            return@use it.toByteArray()
        }
    }
}
