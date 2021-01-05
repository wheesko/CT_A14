package com.VU;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Specialized Utils class for operations on vectors
public class VectorUtils {

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

    // Method to swap bits
    public static Integer changeBit(Integer bit) {
        return bit == 1 ? 0 : 1;
    }
}
