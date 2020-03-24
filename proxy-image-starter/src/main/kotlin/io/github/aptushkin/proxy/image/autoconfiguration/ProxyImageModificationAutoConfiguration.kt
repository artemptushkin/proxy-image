package io.github.aptushkin.proxy.image.autoconfiguration

import io.github.aptushkin.proxy.image.ImageModificationConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Configuration
@Import(ImageModificationConfiguration::class)
@ConditionalOnProperty(prefix = "proxy.image", name = ["enabled"], havingValue = "true", matchIfMissing = true)
class ProxyImageModificationAutoConfiguration
