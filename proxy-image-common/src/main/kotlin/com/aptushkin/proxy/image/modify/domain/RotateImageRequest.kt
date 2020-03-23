package com.aptushkin.proxy.image.modify.domain

import org.imgscalr.Scalr

class RotateImageRequest(
        val rotation: Scalr.Rotation, val format: String
)
