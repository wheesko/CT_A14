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
}
