package io.github.aptushkin.proxy.image.app

import org.springframework.boot.Banner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ImageProxyApplication

fun main(args: Array<String>) {
    runApplication<ImageProxyApplication>(*args) {
        setBannerMode(Banner.Mode.OFF)
    }
}
