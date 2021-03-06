package io.github.aptushkin.proxy.image.modify.config

import org.imgscalr.Scalr
import org.springframework.validation.annotation.Validated

@Validated
class ResizeImageConfig: DefaultImageConfig() {
    var defaultWidth: Int? = null
    var defaultHeight: Int? = null

    var defaultMode: Scalr.Mode? = null
    var defaultMethod: Scalr.Method? = null

    override fun isDefaultsAvailable(): Boolean = defaultHeight != null || defaultWidth != null || defaultMode != null || defaultMethod != null
}
