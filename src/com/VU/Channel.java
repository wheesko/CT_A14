package com.VU;

import java.util.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.stream.Collectors;

import static com.VU.Utils.BMP_SKIP_SIZE;

/*
    Channel class with probability of distortion
*/
public class Channel {

    // Probability that a single bit will be erroneous
    private BigDecimal probability;

    public Channel() {
    }

    /*
        Send message through channel, display error positions, original message and output message.
        User can manually change the output, to set errors where he wants to.

        input - original encoded message to be sent through channel
        returns message that passed through channel which may or may not have errors.

    */
    public List<Integer> sendVector(List<Integer> input) {
        ArrayList<Integer> output = new ArrayList<>();
        List<Integer> errorPositions = new ArrayList<>();

        // Check bit by bit if error will be made
        for (int i = 0; i < input.size(); i++) {
            // If random value between zero to one is smaller than probability, error will be made, else keep the bit as is
            if (BigDecimal.valueOf(Math.random()).compareTo(probability) < 0) {
                errorPositions.add(i + 1);
                output.add(VectorUtils.changeBit(input.get(i)));
            } else {
                output.add(input.get(i));
            }
        }

        printVector(output, "Vector after sending: ");
        List<Integer> originalOutput = output;

        showErrorPositions(errorPositions);
        output = (ArrayList<Integer>) changeErrors(originalOutput);

        if (output.isEmpty()) {
            output = (ArrayList<Integer>) changeErrors(originalOutput);
        }

        printVector(output, "Final channel output: ");

        return output;
    }

    public List<Integer> sendVectorTest(List<Integer> input) {
        ArrayList<Integer> output = new ArrayList<>();

        // Check bit by bit if error will be made
        for (Integer integer : input) {
            // If random value between zero to one is smaller than probability, error will be made, else keep the bit as is
            if (BigDecimal.valueOf(Math.random()).compareTo(probability) < 0) {
                output.add(VectorUtils.changeBit(integer));
            } else {
                output.add(integer);
            }
        }

        return output;
    }

    // Send text through channel
    public List<Integer> sendText(List<Integer> input) {
        ArrayList<Integer> output = new ArrayList<>();
        // Check bit by bit if error will be made
        for (Integer integer : input) {
            // If random value between zero to one is smaller than probability, error will be made, else keep the bit as is
            if (BigDecimal.valueOf(Math.random()).compareTo(probability) < 0) {
                output.add(VectorUtils.changeBit(integer));
            } else {
                output.add(integer);
            }
        }

        return output;
    }

    public String sendImage(String image) {
        StringBuilder output = new StringBuilder();

        String fileHeader = image.substring(0, BMP_SKIP_SIZE);
        String[] strings = image.split("");
        // Check bit by bit if error will be made
        for (int i = BMP_SKIP_SIZE; i < strings.length; i++) {
            // If random value between zero to one is smaller than probability, error will be made, else keep the bit as is
            if (BigDecimal.valueOf(Math.random()).compareTo(probability) < 0) {
                output.append(ImageUtils.changeBit(strings[i]));
            } else {
                output.append(strings[i]);
            }
        }

        return fileHeader + output.toString();
    }

    public String sendImageEncoded(String image) {
        StringBuilder output = new StringBuilder();

        String[] strings = image.split("");
        // Check bit by bit if error will be made
        for (int i = 0; i < strings.length; i++) {
            // If random value between zero to one is smaller than probability, error will be made, else keep the bit as is
            if (BigDecimal.valueOf(Math.random()).compareTo(probability) < 0) {
                output.append(ImageUtils.changeBit(strings[i]));
            } else {
                output.append(strings[i]);
            }
        }

        return output.toString();
    }


    // Manually change where errors were made
    public List<Integer> changeErrors(List<Integer> original) {
        System.out.println("Modify result after error, press enter to leave unchanged");
        Scanner sc = new Scanner(System.in);
        String result = sc.nextLine().replace(" ", "");

        if(result.isEmpty()) {
            return original;
        }

        return VectorUtils.stringToIntegerList(result);
    }

    public void printVector(List<Integer> vector, String message) {
        System.out.println(message);
        System.out.println(vector.stream().map(Object::toString).collect(Collectors.joining()));
    }

    public void showErrorPositions(List<Integer> errors) {
        if(errors.isEmpty()) {
            System.out.println("No errors made");
        } else {
            System.out.println("Errors were made at positions: ");
            System.out.println(errors.stream().map(Object::toString).collect(Collectors.joining(", ")));
        }
    }

    public BigDecimal getProbability() {
        return probability;
    }

    public void setProbability(BigDecimal probability) {
        this.probability = probability;
    }
}