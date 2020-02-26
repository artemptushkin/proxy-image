package com.aptushkin.proxy.image.modify

interface ByteArrayModifier {
    fun modify(byteArray: ByteArray): ByteArray
}
