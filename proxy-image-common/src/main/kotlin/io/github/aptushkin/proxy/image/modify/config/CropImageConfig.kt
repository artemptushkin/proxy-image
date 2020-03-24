package io.github.aptushkin.proxy.image.modify.config

import org.springframework.validation.annotation.Validated

@Validated
class CropImageConfig: DefaultImageConfig() {
    var defaultWidth: Int? = null
    var defaultHeight: Int? = null

    var defaultX: Int? = null
    var defaultY: Int? = null
}
