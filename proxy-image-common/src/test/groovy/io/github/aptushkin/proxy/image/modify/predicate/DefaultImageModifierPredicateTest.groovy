package io.github.aptushkin.proxy.image.modify.predicate

import io.github.aptushkin.proxy.image.modify.config.DefaultImageConfig
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.http.server.reactive.ServerHttpResponse
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.server.ServerWebExchange

import static org.junit.jupiter.api.Assertions.assertFalse
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class DefaultImageModifierPredicateTest {

    ImageModifierPredicate predicate

    @Test
    void "should return false on non existed header and false property at the config"() {
        //setup
        def onNonExistedHeader = false
        def config = prepareConfig("headerName", "image/.*", onNonExistedHeader)
        predicate = new DefaultImageModifierPredicate(config)

        //when
        ServerWebExchange serverWebExchange = prepareExchange(new HttpHeaders())

        //expect
        assertTrue(
                predicate.test(serverWebExchange) == onNonExistedHeader
        )
    }

    @Test
    void "should return false on non existed header and true property at the config"() {
        //setup
        def onNonExistedHeader = true
        def config = prepareConfig("headerName", "image/.*", onNonExistedHeader)
        predicate = new DefaultImageModifierPredicate(config)

        //when
        ServerWebExchange serverWebExchange = prepareExchange(new HttpHeaders())

        //expect
        assertTrue(
                predicate.test(serverWebExchange) == onNonExistedHeader
        )
    }

    @Test
    void "should return false on empty query params"() {
        //setup
        def config = prepareConfig("headerName", "image/.*", false)
        predicate = new DefaultImageModifierPredicate(config)

        //when
        ServerWebExchange serverWebExchange = prepareExchange(new HttpHeaders([headerName: "image/awesome"]), new LinkedMultiValueMap<String, String>())

        assertFalse(
                predicate.test(serverWebExchange)
        )
    }

    @Test
    void "should return false on non matched header value"() {
        //setup
        def config = prepareConfig("headerName", "image/.*", false)
        predicate = new DefaultImageModifierPredicate(config)

        //when
        ServerWebExchange serverWebExchange = prepareExchange(new HttpHeaders([headerName: "application/json"]), new LinkedMultiValueMap<String, String>([someQuery: "val"]))

        assertFalse(
                predicate.test(serverWebExchange)
        )
    }

    @Test
    void "should return true on existed query params and matched header value"() {
        //setup
        def config = prepareConfig("headerName", "image/.*", false)
        predicate = new DefaultImageModifierPredicate(config)

        //when
        ServerWebExchange serverWebExchange = prepareExchange(new HttpHeaders([headerName: "image/awesome"]), new LinkedMultiValueMap<String, String>([someQuery: "val"]))

        assertTrue(
                predicate.test(serverWebExchange)
        )
    }

    static DefaultImageConfig prepareConfig(String headerName, String headerValueRegexp, boolean onNonExistedHeader) {
        return new DefaultImageConfig().with(true, {
            it.responseHeaderName = headerName
            it.regexp = headerValueRegexp
            it.onNotExistedHeader = onNonExistedHeader
        }) as DefaultImageConfig
    }

    static ServerWebExchange prepareExchange(HttpHeaders headers) {
        def exchange = mock(ServerWebExchange)
        ServerHttpResponse response = mock(ServerHttpResponse)
        when response.headers thenReturn headers
        when exchange.response thenReturn response
        exchange
    }

    static ServerWebExchange prepareExchange(HttpHeaders headers, MultiValueMap<String, String> query) {
        def exchange = mock(ServerWebExchange)
        ServerHttpResponse response = mock(ServerHttpResponse)
        when response.headers thenReturn headers
        ServerHttpRequest request = mock(ServerHttpRequest)
        when request.queryParams thenReturn query

        when exchange.response thenReturn response
        when exchange.request thenReturn request
        exchange
    }
}
