package io.github.aptushkin.proxy.image.modify.config

import org.imgscalr.Scalr

class RotateImageConfig: DefaultImageConfig() {
    var defaultRotation: Scalr.Rotation? = null

    override fun isDefaultsAvailable(): Boolean = defaultRotation != null
}
