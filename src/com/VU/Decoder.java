package com.VU;

import java.util.ArrayList;
import java.util.List;

//Convolutional decoder
public class Decoder {

    public List<Integer> decode(List<Integer> encodedMessage) {
        List<Integer> decodedMessage = new ArrayList<>();
        EncoderMemory encoderMemory = new EncoderMemory();
        FeedbackDecoderMemory feedbackDecoderMemory = new FeedbackDecoderMemory();

        for (int i = 0; i < encodedMessage.size(); i = i + 2) {
            decodedMessage.add(
                    Utils.binarySum(
                            encoderMemory.getLastBit(),
                            feedbackDecoderMemory.getMDEValue(
                                    Utils.binarySum(
                                            encoderMemory.getMemorySum(encodedMessage.get(i)),
                                            encodedMessage.get(i+1)
                                    )
                            )
                    )
            );
            feedbackDecoderMemory.add(
                    Utils.binarySum(encodedMessage.get(i+1),
                    encoderMemory.getMemorySum(encodedMessage.get(i)))
            );
            encoderMemory.add(encodedMessage.get(i));
        }

        return decodedMessage.subList(6, decodedMessage.size());
    }

    public String decode(String encodedMessage) {
        StringBuilder decodedMessage = new StringBuilder();
        EncoderMemory encoderMemory = new EncoderMemory();
        FeedbackDecoderMemory feedbackDecoderMemory = new FeedbackDecoderMemory();

        for (int i = 0; i < encodedMessage.length(); i = i + 2) {
            Integer bitOne = Integer.parseInt(String.valueOf(encodedMessage.charAt(i)));
            Integer bitTwo = Integer.parseInt(String.valueOf(encodedMessage.charAt(i+1)));

            decodedMessage.append(
                    Utils.binarySum(
                            encoderMemory.getLastBit(),
                            feedbackDecoderMemory.getMDEValue(
                                    Utils.binarySum(
                                            encoderMemory.getMemorySum(bitOne),
                                            bitTwo
                                    )
                            )
                    )
            );
            feedbackDecoderMemory.add(
                    Utils.binarySum(bitTwo,
                            encoderMemory.getMemorySum(bitOne))
            );
            encoderMemory.add(bitOne);
        }

        return decodedMessage.substring(6, decodedMessage.length());
    }
}
