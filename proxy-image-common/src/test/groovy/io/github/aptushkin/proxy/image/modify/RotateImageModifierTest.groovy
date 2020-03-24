package io.github.aptushkin.proxy.image.modify

import io.github.aptushkin.proxy.image.modify.domain.RotateImageRequest
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

class RotateImageModifierTest {

    ByteArrayModifier imageModifier

    def inputImage

    @BeforeEach
    void setUp() {
        inputImage = getImage("input.png")
    }

    static Stream<Arguments> input() {
        return Stream.of(
                arguments(new RotateImageRequest(Scalr.Rotation.CW_180, "png"), "rotate-cw_180.png"),
                arguments(new RotateImageRequest(Scalr.Rotation.CW_270, "jpg"), "rotate-cw_270.jpg")
        )
    }

    @ParameterizedTest
    @MethodSource("input")
    void "should resize image by width and height"(def rotateRequest, def expectedFileName) {
        //setup
        imageModifier = new RotateImageModifier(rotateRequest)
        def expected = getImage(expectedFileName)

        //then
        byte[] actual = imageModifier.modify(inputImage)

        //expect
        assertTrue(
                compareImages(expected, actual)
        )
    }
}
