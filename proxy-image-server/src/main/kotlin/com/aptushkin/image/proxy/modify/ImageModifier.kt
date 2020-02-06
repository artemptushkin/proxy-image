package com.aptushkin.image.proxy.modify

interface ImageModifier {
    fun modify(byteArray: ByteArray): ByteArray
}
