package com.VU;

import java.util.*;

// Convolutional encoder
public class Encoder {

    // Encode
    public List<Integer> encode(List<Integer> inputOriginal) {
        EncoderMemory memory = new EncoderMemory();
        List<Integer> output = new ArrayList<>();
        List<Integer> input = new ArrayList<>(inputOriginal);

        input.addAll(Arrays.asList(0, 0, 0, 0, 0, 0)); // Pad the input so that memory is cleared at the end

        for (Integer i: input) {
            output.add(i); //Current input bit
            output.add(memory.getMemorySum(i)); //Summed input bit with memory values
            memory.add(i); // Push input to memory
        }

        return output;
    }

    public String encode(String inputOriginal) {
        EncoderMemory memory = new EncoderMemory();
        StringBuilder output = new StringBuilder();
        StringBuilder input = new StringBuilder();
        input.append(inputOriginal);

        input.append("000000"); // Pad the input so that memory is cleared at the end

        for (String i: input.toString().split("")) {
            output.append(i); //Current input bit
            output.append(memory.getMemorySum(Integer.parseInt(i))); //Summed input bit with memory values
            memory.add(Integer.parseInt(i)); // Push input to memory
        }

        return output.toString();
    }
}
