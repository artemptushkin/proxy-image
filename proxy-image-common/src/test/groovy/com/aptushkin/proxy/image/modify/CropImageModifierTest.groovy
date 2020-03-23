package com.aptushkin.proxy.image.modify

import com.aptushkin.proxy.image.modify.domain.CropRequest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource

import java.util.stream.Stream

import static com.aptushkin.proxy.image.ImageTestUtils.compareImages
import static com.aptushkin.proxy.image.ImageTestUtils.getImage
import static org.junit.jupiter.api.Assertions.assertTrue
import static org.junit.jupiter.params.provider.Arguments.arguments

class CropImageModifierTest {

    ByteArrayModifier imageModifier

    def inputImage

    @BeforeEach
    void setUp() {
        inputImage = getImage("input.png")
    }

    static Stream<Arguments> input() {
        return Stream.of(
                arguments(new CropRequest(600, 500, "png", null, null), "crop-600x500.png"),
                arguments(new CropRequest(600, 500, "jpg", null, null), "crop-600x500.jpg"),
                arguments(new CropRequest(600, 500, "png", 400, 100), "crop-x400y100w600h500.png")
        )
    }

    @ParameterizedTest
    @MethodSource("input")
    void "should crop image by width and height"(def cropRequest, def expectedFileName) {
        //setup
        imageModifier = new CropImageModifier(cropRequest)
        def expected = getImage(expectedFileName)

        //then
        byte[] actual = imageModifier.modify(inputImage)

        //expect
        assertTrue(
                compareImages(expected, actual)
        )
    }
}
