package com.VU;

import java.util.List;
import java.util.stream.Collectors;

// Specialized Utils class for operations on text
public class TextUtils {

    public static List<Integer> stringToBits(String input) {
        StringBuilder binary = new StringBuilder();
        for (byte b : input.getBytes())
        {
            int intValue = b;
            for (int i = 0; i < 8; i++)
            {
                binary.append((intValue & 128) == 0 ? 0 : 1);
                intValue <<= 1;
            }
        }

        return VectorUtils.stringToIntegerList(binary.toString());
    }

    public static String bitsToString(List<Integer> bits) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bits.size(); i += 8) {
            String byteString = bits.subList(i, i+8).stream().map(String::valueOf).collect(Collectors.joining());
            result.append((char) Integer.parseInt(byteString,2));
        }
        return result.toString();
    }
}
