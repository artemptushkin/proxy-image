package io.github.aptushkin.proxy.image.modify

import io.github.aptushkin.proxy.image.modify.domain.ResizeRequest
import org.imgscalr.Scalr
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import java.util.stream.Stream

import static io.github.aptushkin.proxy.image.ImageTestUtils.compareImages
import static io.github.aptushkin.proxy.image.ImageTestUtils.getImage
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.params.provider.Arguments.arguments

class ResizeImageModifierTest {

    ByteArrayModifier imageModifier

    def inputImage

    @BeforeEach
    void setUp() {
        inputImage = getImage("input.png")
    }

    static Stream<Arguments> input() {
        return Stream.of(
                arguments(new ResizeRequest(200, 200, "png", Scalr.Method.AUTOMATIC, Scalr.Mode.AUTOMATIC), "200x200.png"),
                arguments(new ResizeRequest(200, 200, "png", null, null), "200x200.png"),
                arguments(new ResizeRequest(200, 200, "png", Scalr.Method.AUTOMATIC, null), "200x200.png"),
                arguments(new ResizeRequest(100, 300, "jpg", null, Scalr.Mode.AUTOMATIC), "100x300.jpg")
        )
    }

    @ParameterizedTest
    @MethodSource("input")
    void "should resize image by width and height"(def resizeRequest, def expectedFileName) {
        //setup
        imageModifier = new ResizeImageModifier(resizeRequest)
        def expected = getImage(expectedFileName)

        //then
        byte[] actual = imageModifier.modify(inputImage)

        //expect
        assertTrue(
                compareImages(expected, actual)
        )
    }
}
