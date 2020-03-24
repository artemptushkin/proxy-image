package io.github.aptushkin.proxy.image.modify.conversion

import io.github.aptushkin.proxy.image.modify.config.ResizeImageConfig
import org.imgscalr.Scalr
import org.junit.jupiter.api.Test
import org.springframework.http.server.reactive.ServerHttpRequest
import org.springframework.util.LinkedMultiValueMap
import org.springframework.web.server.ServerWebExchange

import static org.junit.jupiter.api.Assertions.assertThrows
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.mockito.Mockito.mock
import static org.mockito.Mockito.when

class ResizeRequestConverterTest {
    ResizeRequestConverter converter

    @Test
    void "should throw exception on width without height"() {
        //setup
        ResizeImageConfig config = prepareConfig(100, null)
        converter = new ResizeRequestConverter(config)
        def request = prepareRequest(
                [
                        width: ["200"]
                ]
                , "http://github.githubassets.com/images/modules/open_graph/github-octocat.png?width=200")
        def requestExchange = prepareExchange(request)

        //then
        assertThrows(IllegalStateException, { -> converter.convert(requestExchange) })
    }

    @Test
    void "should throw exception on height without width"() {
        //setup
        ResizeImageConfig config = prepareConfig(null, 50)
        converter = new ResizeRequestConverter(config)
        def request = prepareRequest(
                [
                        height: ["200"]
                ]
                , "http://github.githubassets.com/images/modules/open_graph/github-octocat.png?height=300")
        def requestExchange = prepareExchange(request)

        //then
        assertThrows(IllegalStateException, { -> converter.convert(requestExchange) })
    }

    @Test
    void "should get format from query"() {
        //setup
        def expectedFormat = "png"
        ResizeImageConfig config = prepareConfig(100, 50)
        def request = prepareRequest(
                [
                        height: ["200"],
                        width : ["300"],
                        format: [expectedFormat]
                ]
                , "http://github.githubassets.com/images/modules/open_graph/github-octocat.png?width=200&height=300")
        def requestExchange = prepareExchange(request)

        converter = new ResizeRequestConverter(config)

        //then
        def actual = converter.convert(requestExchange)

        //expect
        assertTrue(actual.getFormat() == expectedFormat)
    }

    @Test
    void "should get format from config"() {
        //setup
        def expectedFormat = "png"
        ResizeImageConfig config = prepareConfig(100, 50, expectedFormat)
        def request = prepareRequest()
        def requestExchange = prepareExchange(request)

        converter = new ResizeRequestConverter(config)

        //then
        def actual = converter.convert(requestExchange)

        assertTrue(actual.getFormat() == expectedFormat)
    }

    @Test
    void "should get format from url"() {
        //setup
        def expectedFormat = "png"
        ResizeImageConfig config = prepareConfig(200, 300)
        def request = prepareRequest(new LinkedHashMap<String, ?>(), "http://github.githubassets.com/images/modules/open_graph/github-octocat.$expectedFormat?width=200&height=300")
        def requestExchange = prepareExchange(request)

        converter = new ResizeRequestConverter(config)

        //then
        def actual = converter.convert(requestExchange)

        assertTrue(actual.getFormat() == expectedFormat)
    }

    @Test
    void "should get format from url without query"() {
        //setup
        def expectedFormat = "png"
        ResizeImageConfig config = prepareConfig(200, 300)
        def request = prepareRequest(new LinkedHashMap<String, ?>(), "http://github.githubassets.com/images/modules/open_graph/github-octocat.$expectedFormat")
        def requestExchange = prepareExchange(request)

        converter = new ResizeRequestConverter(config)

        //then
        def actual = converter.convert(requestExchange)

        assertTrue(actual.getFormat() == expectedFormat)
    }

    @Test
    void "should get width and height from query"() {
        //setup
        def expectedWidth = "200"
        def expectedHeight = "300"
        ResizeImageConfig config = prepareConfig(100, 50)
        def request = prepareRequest(
                [
                        width: [expectedWidth],
                        height : [expectedHeight]
                ]
                , "http://github.githubassets.com/images/modules/open_graph/github-octocat.png?width=$expectedWidth&height=$expectedHeight")
        def requestExchange = prepareExchange(request)

        converter = new ResizeRequestConverter(config)

        //then
        def actual = converter.convert(requestExchange)

        //expect
        assertTrue(actual.getWidth() == expectedWidth.toInteger())
        assertTrue(actual.getHeight() == expectedHeight.toInteger())
    }

    @Test
    void "should get mode and method from query"() {
        //setup
        def expectedMode = Scalr.Mode.AUTOMATIC
        def expectedMethod = Scalr.Method.BALANCED
        ResizeImageConfig config = prepareConfig(expectedMode, expectedMethod)
        def request = prepareRequest(
                [
                        mode: [expectedMode.name()],
                        method : [expectedMethod.name()]
                ]
                , "http://github.githubassets.com/images/modules/open_graph/github-octocat.png?mode=$expectedMode&method=$expectedMethod")
        def requestExchange = prepareExchange(request)

        converter = new ResizeRequestConverter(config)

        //then
        def actual = converter.convert(requestExchange)

        //expect
        assertTrue(actual.mode == expectedMode)
        assertTrue(actual.method == expectedMethod)
    }

    @Test
    void "should get mode and method from config"() {
        //setup
        def expectedMode = Scalr.Mode.AUTOMATIC
        def expectedMethod = Scalr.Method.BALANCED
        ResizeImageConfig config = prepareConfig(expectedMode, expectedMethod)
        def request = prepareRequest([:], "http://github.githubassets.com/images/modules/open_graph/github-octocat.png")
        def requestExchange = prepareExchange(request)

        converter = new ResizeRequestConverter(config)

        //then
        def actual = converter.convert(requestExchange)

        //expect
        assertTrue(actual.mode == expectedMode)
        assertTrue(actual.method == expectedMethod)
    }

    static ServerWebExchange prepareExchange(def request) {
        def exchange = mock(ServerWebExchange)
        when exchange.request thenReturn request
        exchange
    }

    static ServerHttpRequest prepareRequest(LinkedHashMap<String, ?> queries, def uri) {
        def request = mock(ServerHttpRequest)
        when request.getURI() thenReturn new URI(uri)
        when request.queryParams thenReturn new LinkedMultiValueMap(queries)
        request
    }

    static ServerHttpRequest prepareRequest() {
        def request = mock(ServerHttpRequest)
        when request.getURI() thenReturn new URI("/test")
        when request.queryParams thenReturn new LinkedMultiValueMap()
        request
    }

    static ResizeImageConfig prepareConfig(Scalr.Mode mode = null, Scalr.Method method = null) {
        ResizeImageConfig config = mock(ResizeImageConfig)
        when config.defaultMethod thenReturn method
        when config.defaultMode thenReturn mode
        config
    }

    static ResizeImageConfig prepareConfig(def width, def height = 222, def format = null, Scalr.Mode mode = null, Scalr.Method method = null) {
        ResizeImageConfig config = mock(ResizeImageConfig)
        when config.defaultWidth thenReturn width
        when config.defaultHeight thenReturn height
        when config.defaultFormat thenReturn format
        when config.defaultMethod thenReturn method
        when config.defaultMode thenReturn mode
        config
    }
}
