package com.aptushkin.proxy.image.modify.scalr

import org.imgscalr.Scalr

fun String.toScalrMethod(): Scalr.Method? = Scalr.Method
        .values()
        .find { it.name.equals(this, ignoreCase = true) }

fun String.toScalrMode(): Scalr.Mode? = Scalr.Mode
        .values()
        .find { it.name.equals(this, ignoreCase = true) }

fun String.toScalrRotation(): Scalr.Rotation? = Scalr.Rotation
        .values()
        .find { it.name.equals(this, ignoreCase = true) }
