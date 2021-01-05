package com.VU;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;

// Specialized utils class for operations on images
public class ImageUtils {
    public static String bytesToBinaryString(byte[] bytes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b: bytes) {
            stringBuilder.append(Integer.toBinaryString((b & 0xFF) + 0x100).substring(1));
        }

        return stringBuilder.toString();
    }

    public static byte[] binaryStringToBytes(String binary) {
        return new BigInteger(binary, 2).toByteArray();
    }

    public static byte[] imageToBytes(BufferedImage img) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(img, "bmp", baos);

        return baos.toByteArray();
    }

    public static void saveImage(byte[] imageBytes, String filename) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
        BufferedImage byteImage = ImageIO.read(byteArrayInputStream);
        ImageIO.write(byteImage, "bmp", new File(filename + ".bmp") );
    }

    public static String changeBit(String bit) {
        return bit.equals("1") ? "0" : "1";
    }
}
