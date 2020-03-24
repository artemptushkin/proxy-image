package io.github.aptushkin.proxy.image.modify.domain

import org.imgscalr.Scalr

data class ResizeRequest(
        val width: Int, val height: Int, val format: String, val method: Scalr.Method?, val mode: Scalr.Mode?
)
