package com.aptushkin.proxy.image.modify

interface ImageModifier {
    fun modify(byteArray: ByteArray): ByteArray
}
