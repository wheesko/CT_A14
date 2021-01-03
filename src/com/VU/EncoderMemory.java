package com.VU;

import java.util.*;

public class EncoderMemory {
    private List<Integer> memory;

    public EncoderMemory() {
        memory = new ArrayList<>(Arrays.asList(0, 0, 0, 0, 0, 0));
    }

    // Push last bit out, shift all bits to right, set first one to current input
    public void add(Integer bit) {
        memory.remove(5);
        memory.add(0, bit);
    }

    // Get sum of input and bits in memory
    public Integer getMemorySum(Integer currentInput) {
        return (memory.get(1) + memory.get(4) + memory.get(5) + currentInput) % 2 == 0 ? 0 : 1;
    }

    public Integer getLastBit() {
        return memory.get(5);
    }
}
