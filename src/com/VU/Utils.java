package com.VU;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static Integer majorityDecisionElement(List<Integer> list) { // If list contains more than 2 ones, return 1, else 0
        return list.stream().mapToInt(Integer::intValue).sum() > 2 ? 1 : 0;
    }

    public static Integer binarySum(Integer int1, Integer int2) {
        return (int1 + int2) % 2 == 0 ? 0 : 1;
    }
}
