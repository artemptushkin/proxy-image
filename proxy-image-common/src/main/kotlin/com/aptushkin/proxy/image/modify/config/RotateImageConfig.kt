package com.aptushkin.proxy.image.modify.config

import org.imgscalr.Scalr

class RotateImageConfig: DefaultImageConfig() {
    var defaultRotation: Scalr.Rotation? = null
}
