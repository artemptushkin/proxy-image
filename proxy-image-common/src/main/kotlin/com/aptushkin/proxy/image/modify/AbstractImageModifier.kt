package com.aptushkin.proxy.image.modify

import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

abstract class AbstractImageModifier(private val format: String) : ByteArrayModifier {

    override fun modify(byteArray: ByteArray): ByteArray {
        val inputImage = ByteArrayInputStream(byteArray).use {
            return@use ImageIO.read(it)
        }

        val scaledImage: BufferedImage = this.modifyImage(inputImage)

        return ByteArrayOutputStream().use {
            ImageIO.write(scaledImage, format, it)
            return@use it.toByteArray()
        }
    }

    protected abstract fun modifyImage(image: BufferedImage): BufferedImage
}
