package com.VU;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// Error correcting part of Feedback Decoder
public class FeedbackDecoderMemory {
    private List<Integer> memory;

    public FeedbackDecoderMemory() {
        memory = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
    }

    // Push last bit out, shift all bits to right, set first one to current input
    public void add(Integer bit) {
        memory.add(0, Utils.binarySum(bit, getMDEValue(bit)));
        memory.remove(6);
        memory.set(1, Utils.binarySum(memory.get(1), getMDEValue(bit)));
        memory.set(4, Utils.binarySum(memory.get(4), getMDEValue(bit)));
    }

    // Get majority decision element value
    public Integer getMDEValue(Integer currentInput) {
        return Utils.majorityDecisionElement(Arrays.asList(
                currentInput,
                memory.get(0),
                memory.get(3),
                memory.get(5)
        ));
    }
}
