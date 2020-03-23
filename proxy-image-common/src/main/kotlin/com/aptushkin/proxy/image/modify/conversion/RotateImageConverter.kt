package com.aptushkin.proxy.image.modify.conversion

import com.aptushkin.proxy.image.modify.config.RotateImageConfig
import com.aptushkin.proxy.image.modify.domain.RotateImageRequest
import com.aptushkin.proxy.image.modify.scalr.toScalrRotation
import org.imgscalr.Scalr
import org.springframework.core.convert.converter.Converter
import org.springframework.web.server.ServerWebExchange

class RotateImageConverter(private val config: RotateImageConfig): Converter<ServerWebExchange, RotateImageRequest> {
    override fun convert(serverWebExchange: ServerWebExchange): RotateImageRequest {
        val uri = serverWebExchange.request.uri.toString()
        val params = serverWebExchange.request.queryParams

        val format: String = if (params.containsKey("format")) {
            params.getFirst("format")
        } else {
            if (uri.contains(".")) {
                if (uri.contains("?")) uri.substring(uri.lastIndexOf(".") + 1, uri.lastIndexOf("?"))
                else uri.substring(uri.lastIndexOf(".") + 1, uri.length)
            } else config.defaultFormat
        } ?: throw IllegalStateException("Either format must exists at the request params or at the application properties as defaultFormat")

        val rotation: Scalr.Rotation = params["rotation"]?.firstOrNull()?.toScalrRotation() ?: config.defaultRotation
        ?: throw IllegalStateException("Either rotation must exists at the request params or at the application properties")

        return RotateImageRequest(rotation, format)
    }

}
