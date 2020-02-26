package com.aptushkin.proxy.image.modify.config

import org.imgscalr.Scalr
import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@Validated
class ResizeImageConfig: ModifyResponseBodyGatewayFilterFactory.Config() {
    @NotEmpty
    lateinit var responseHeaderName: String
    @NotEmpty
    lateinit var regexp: String
    var onNotExistedHeader = false
    var defaultWidth: Int? = null
    var defaultHeight: Int? = null
    var defaultFormat: String? = null

    var defaultMode: Scalr.Mode? = null
    var defaultMethod: Scalr.Method? = null
}