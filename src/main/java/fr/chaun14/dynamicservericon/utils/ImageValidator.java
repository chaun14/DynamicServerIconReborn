package fr.chaun14.dynamicservericon.utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class ImageValidator {

    /**
     * Validates that the given file:
     * - exists
     * - is a PNG
     * - is a valid image
     * - is exactly 64x64 pixels
     *
     * @param file the file to validate
     * @return null if valid, otherwise an error message
     */
    public static String validateIconFile(File file) {
        String name = file.getName().toLowerCase();

        if (!name.endsWith(".png")) {
            return "Only PNG files are supported.";
        }

        if (!file.exists()) {
            return "File not found: " + file.getName();
        }

        try {
            BufferedImage image = ImageIO.read(file);
            if (image == null) {
                return "Invalid image file.";
            }
            if (image.getWidth() != 64 || image.getHeight() != 64) {
                return "Image must be exactly 64x64 pixels.";
            }
        } catch (IOException e) {
            return "Failed to read the image: " + e.getMessage();
        }

        return null; // valid
    }
}
