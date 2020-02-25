package com.aptushkin.proxy.image.modify.config

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
}
