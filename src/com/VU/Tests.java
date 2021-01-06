package com.VU;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.RoundingMode;
import java.util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

import static com.VU.Utils.BMP_SKIP_SIZE;
import static java.util.Collections.max;

public class Tests {
    private static Channel channel = new Channel();
    private static Encoder encoder = new Encoder();
    private static Decoder decoder = new Decoder();
    private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {

        defineChannel();
        //runVectorTests();
        //runTextTests();
        runImageTests("C:\\Users\\Vytautas\\Desktop\\test64.bmp", "64");
        runImageTests("C:\\Users\\Vytautas\\Desktop\\test256.bmp", "256");
        runImageTests("C:\\Users\\Vytautas\\Desktop\\test512.bmp", "512");
    }

    public static void runVectorTests() {
        Integer passedEncodedTests = 0;
        Integer passedNonEncodedTests = 0;

        for (int i = 0; i < 10000; i++) {
            // Get random vector
            List<Integer> vector = generateRandomVector(100);
            // Encode
            List<Integer> encodedVector = encoder.encode(vector);
            // Send through channel
            List<Integer> distortedVector = channel.sendVectorTest(encodedVector);
            // Decode
            List<Integer> decodedVector = decoder.decode(distortedVector);
            // Assert randomVector = decodedVector
            if (decodedVector.equals(vector)) {
                passedEncodedTests++;
            }
        }

        for (int i = 0; i < 10000; i++) {
            // Get random vector
            List<Integer> vector = generateRandomVector(100);
            // Send through channel
            List<Integer> distortedVector = channel.sendVectorTest(vector);
            // Assert randomVector = decodedVector
            if (distortedVector.equals(vector)) {
                passedNonEncodedTests++;
            }
        }

        Double percentageEncoded = (double) passedEncodedTests / 100;
        Double percentageNonEncoded = (double) passedNonEncodedTests / 100;


        System.out.println("Percentage with encoding: " + percentageEncoded);
        System.out.println("Percentage without encoding: " + percentageNonEncoded);
        System.out.println("Improvement: " + (double) (percentageEncoded / percentageNonEncoded));
    }

    public static void runTextTests() {
        String randomString;

        List<String> maximums = new ArrayList<>();
        List<BigDecimal> maxList = new ArrayList<>();

        for (int i = 2; i <= 8192; i*= 8) {

            // Get random vector
            randomString = generateRandomText(i);
            for (int z = 0; z < 100; z += 1) {
                // Send through channel
                List<Integer> stringBits = channel.sendText(encoder.encode(TextUtils.stringToBits(randomString)));

                String randomStringReconstructed = TextUtils.bitsToString(decoder.decode(stringBits));
                // Check how many characters different
                BigDecimal similarity = BigDecimal.valueOf(getStringSimilarity(randomStringReconstructed, randomString));
                maxList.add(similarity);
            }
            maximums.add("Maximum similarity for string of length "
                    + i + " : " +
                    max(maxList));
            maxList.clear();
        }

        maximums.forEach(System.out::println);
    }

    public static void runImageTests(String path, String size) throws IOException {
        try {
            File file = new File(path);
            BufferedImage originalImage = ImageIO.read(file);
            byte[] imageBytes = ImageUtils.imageToBytes(originalImage);
            String binaryImage = ImageUtils.bytesToBinaryString(imageBytes);

            //C:\Users\Vytautas\Desktop\test3.bmp
            //Send without encoding
            String binaryImageAfterChannel = channel.sendImage(binaryImage);
            byte[] imageBytesReconstructed = ImageUtils.binaryStringToBytes(binaryImageAfterChannel);

            ImageUtils.saveImage(imageBytesReconstructed, "probability"
                    + channel.getProbability().toString().replace(".", "")
                    + "unencoded"
                    + size);

            //Send with encoding
            String imageHeader = binaryImage.substring(0, BMP_SKIP_SIZE);
            String imageData = binaryImage.substring(BMP_SKIP_SIZE);

            String encodedBinaryImage = encoder.encode(imageData);
            String binaryImageAfterChannel1 = channel.sendImageEncoded(encodedBinaryImage);
            String decodedBinaryImage = decoder.decode(binaryImageAfterChannel1);
            byte[] imageBytesReconstructed1 = ImageUtils.binaryStringToBytes(imageHeader + decodedBinaryImage);

            ImageUtils.saveImage(imageBytesReconstructed1, "probability"
                    + channel.getProbability().toString().replace(".", "")
                    + "encoded"
                    + size);
        } catch (IOException exception){
            System.out.println("Image not found or invalid");
        }
    }

    public static List<Integer> generateRandomVector(int n){
        ArrayList<Integer> list = new ArrayList<Integer>(n);
        Random random = new Random();

        for (int i = 0; i < n; i++)
        {
            list.add(random.nextInt(2));
        }
        return list;
    }

    public static String generateRandomText(int length) {
        int leftLimit = 97; // Ascii a
        int rightLimit = 122; // Ascii z

        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static Double getStringSimilarity(String s1, String s2) {
        int sameCount = 0;

        for (int i = 0; i < s1.length(); i++) {
            if (s1.charAt(i) == s2.charAt(i)) {
                sameCount++;
            }
        }

        return (double) sameCount / s1.length();
    }

    public static void defineChannel() { //Set channel probability after each scenario
        System.out.println("Enter channel probability p, 0 <= p <= 1: ");
        String probability = sc.nextLine();
        probability = probability.replace(",", ".");
        try {
            Double parsedProbability = Double.parseDouble(probability);
            if (parsedProbability.compareTo(0D) < 0 || parsedProbability.compareTo(1D) > 0) {
                System.out.println("Bad format, please try again");
                defineChannel();
            } else {
                channel.setProbability(BigDecimal.valueOf(parsedProbability));
            }
        } catch (NumberFormatException ex) {
            System.out.println("Bad format, please try again");
            defineChannel();
        }
    }
}
