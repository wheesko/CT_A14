package com.VU;

import java.util.*;

// General Utils class used for mathematical operations
public class Utils {
    public static final int BMP_SKIP_SIZE = (14 * 8) + (124 * 8);

    // If list contains more than 2 ones, return 1, else 0
    public static Integer majorityDecisionElement(List<Integer> list) {
        return list.stream().mapToInt(Integer::intValue).sum() > 2 ? 1 : 0;
    }

    public static Integer binarySum(Integer int1, Integer int2) {
        return (int1 + int2) % 2 == 0 ? 0 : 1;
    }
}
