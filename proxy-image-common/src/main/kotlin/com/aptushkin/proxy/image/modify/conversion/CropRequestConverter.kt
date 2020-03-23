package com.aptushkin.proxy.image.modify.conversion

import com.aptushkin.proxy.image.modify.config.CropImageConfig
import com.aptushkin.proxy.image.modify.domain.CropRequest
import org.springframework.core.convert.converter.Converter
import org.springframework.web.server.ServerWebExchange

class CropRequestConverter(private val config: CropImageConfig) : Converter<ServerWebExchange, CropRequest> {

    override fun convert(serverWebExchange: ServerWebExchange): CropRequest {
        val uri = serverWebExchange.request.uri.toString()
        val params = serverWebExchange.request.queryParams

        val format: String = if (params.containsKey("format")) {
            params.getFirst("format")
        } else {
            if (uri.contains(".")) {
                if (uri.contains("?")) uri.substring(uri.lastIndexOf(".") + 1, uri.lastIndexOf("?"))
                else uri.substring(uri.lastIndexOf(".") + 1, uri.length)
            } else config.defaultFormat
        } ?: throw IllegalStateException("Either format must exists at the request params or a the application properties as defaultFormat")

        val width: Int? = params["width"]?.firstOrNull()?.toInt() ?: config.defaultWidth
        val height: Int? = params["height"]?.firstOrNull()?.toInt() ?: config.defaultHeight

        if (width == null || height == null) throw IllegalStateException("Either width & height must exist at the request params or at " +
                "the application properties as defaultWidth / defaultHeight")

        val x: Int? = params["x"]?.firstOrNull()?.toInt() ?: config.defaultX
        val y: Int? = params["y"]?.firstOrNull()?.toInt() ?: config.defaultY

        return CropRequest(
                width, height, format, x, y
        )
    }

}
