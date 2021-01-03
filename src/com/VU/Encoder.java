package com.VU;

import java.util.*;

// Convolutional encoder
public class Encoder {

    // Encode
    public List<Integer> encode(List<Integer> input) {
        EncoderMemory memory = new EncoderMemory();
        List<Integer> output = new ArrayList<>();

        input.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); // Pad the input so that memory is cleared at the end

        for (Integer i: input) {
            output.add(i); //Current input bit
            output.add(memory.getMemorySum(i)); //Summed input bit with memory values
            memory.add(i); // Push input to memory
        }

        return output;
    }

}
