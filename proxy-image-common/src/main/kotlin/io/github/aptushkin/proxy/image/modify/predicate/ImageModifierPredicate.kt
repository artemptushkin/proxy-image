package io.github.aptushkin.proxy.image.modify.predicate

import org.springframework.web.server.ServerWebExchange
import java.util.function.Predicate

interface ImageModifierPredicate: Predicate<ServerWebExchange>
