package io.github.aptushkin.proxy.image.modify.config

import org.springframework.cloud.gateway.filter.factory.rewrite.ModifyResponseBodyGatewayFilterFactory
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.NotEmpty

@Validated
abstract class DefaultImageConfig: ModifyResponseBodyGatewayFilterFactory.Config() {
    @NotEmpty
    lateinit var responseHeaderName: String
    @NotEmpty
    lateinit var regexp: String
    var onNotExistedHeader = false

    var defaultFormat: String? = null

    abstract fun isDefaultsAvailable(): Boolean
}