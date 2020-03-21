package com.aptushkin.proxy.image;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ImageTestUtils {
    private ImageTestUtils() {}

    public static byte[] getImage(String fileName) throws Exception {
        return Files.readAllBytes(Paths.get(ImageTestUtils.class.getClassLoader().getResource(fileName).toURI()));
    }

    public static boolean compareImages(byte[] imageA, byte[] imageB) throws Exception {
        ByteArrayInputStream is1 = new ByteArrayInputStream(imageA);
        ByteArrayInputStream is2 = new ByteArrayInputStream(imageB);
        return compareImages(ImageIO.read(is1), ImageIO.read(is2));
    }

    private static boolean compareImages(BufferedImage imgA, BufferedImage imgB) {
        // The images must be the same size.
        if (imgA.getWidth() != imgB.getWidth() || imgA.getHeight() != imgB.getHeight()) {
            return false;
        }

        int width  = imgA.getWidth();
        int height = imgA.getHeight();

        // Loop over every pixel.
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // Compare the pixels for equality.
                if (imgA.getRGB(x, y) != imgB.getRGB(x, y)) {
                    return false;
                }
            }
        }

        return true;
    }
}
