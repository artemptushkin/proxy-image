package com.aptushkin.image.proxy.modify

import org.springframework.web.server.ServerWebExchange
import java.util.function.Predicate

interface ImageModifierPredicate: Predicate<ServerWebExchange>
