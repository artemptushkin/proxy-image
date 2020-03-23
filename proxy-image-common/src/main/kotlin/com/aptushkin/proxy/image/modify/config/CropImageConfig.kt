package com.aptushkin.proxy.image.modify.config

import org.springframework.validation.annotation.Validated

@Validated
class CropImageConfig: DefaultImageConfig() {
    var defaultWidth: Int? = null
    var defaultHeight: Int? = null
    var defaultFormat: String? = null

    var defaultX: Int? = null
    var defaultY: Int? = null
}
