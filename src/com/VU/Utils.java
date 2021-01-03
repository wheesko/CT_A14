package com.VU;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Utils {
    private static String BINARY_REGEX = "\\b[01]+\\b";

    public static List<Integer> stringToIntegerList(String input) {
        Pattern p = Pattern.compile(BINARY_REGEX);
        Matcher m = p.matcher(input);
        List<Integer> vector = new ArrayList<>();

        if(m.find()) { //Check if is valid binary vector (only 0 and 1 allowed)
            for (String c: input.split("")) { //Convert text to numbers
                vector.add(Integer.parseInt(c));
            }
        } else {
            System.out.println("Invalid vector, please try again");
        }

        return vector;
    }

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

        return Utils.stringToIntegerList(binary.toString());
    }

    public static String bitsToString(List<Integer> bits) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < bits.size(); i += 8) {
            String byteString = bits.subList(i, i+8).stream().map(String::valueOf).collect(Collectors.joining());
            result.append((char) Integer.parseInt(byteString,2));
        }
        return result.toString();
    }

    public static Integer majorityDecisionElement(List<Integer> list) { // If list contains more than 2 ones, return 1, else 0
        return list.stream().mapToInt(Integer::intValue).sum() > 2 ? 1 : 0;
    }

    public static Integer binarySum(Integer int1, Integer int2) {
        return (int1 + int2) % 2 == 0 ? 0 : 1;
    }
}
