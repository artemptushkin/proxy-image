package io.github.aptushkin.proxy.image.modify.domain

class CropRequest(
        val width: Int, val height: Int, val format: String, val x: Int?, val y: Int?
)