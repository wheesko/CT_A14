package com.VU;

import java.util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

public class Tests {
    private static Channel channel = new Channel();
    private static Encoder encoder = new Encoder();
    private static Decoder decoder = new Decoder();

    public static void main(String[] args) {
        Integer passedTests = 0;

        channel.setProbability(BigDecimal.valueOf(0.05f));

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
                passedTests++;
            }
        }

        Double percentage = (double) passedTests / 100;

        System.out.println(percentage);
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
}
