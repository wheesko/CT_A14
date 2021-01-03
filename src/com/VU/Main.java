package com.VU;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	private static Channel channel = new Channel();
	private static Encoder encoder = new Encoder();
	private static Decoder decoder = new Decoder();
	private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) throws IOException {
		greet();
    }

    public static void greet() throws IOException { //Main greeting scenario
    	System.out.println();
		System.out.println("Enter scenario by choosing a number between 1-4: ");
		System.out.println("1: Vector");
		System.out.println("2: Text");
		System.out.println("3: Image");
		System.out.println("4: Exit program");
		getScenario();
	}

    public static void getScenario() throws IOException { //Method for determining what scenario to pick
		String scenario = sc.next();

		switch (scenario) {
			case "1":
				defineChannel();
				vectorScenario();
				break;
			case "2":
				defineChannel();
				textScenario();
				break;
			case "3":
				defineChannel();
				imageScenario();
				break;
			case "4":
				exitScenario();
				break;
			default:
				System.out.println("Invalid input, please try again");
				greet();
				break;
		}
	}

	public static void defineChannel() { //Set channel probability after each scenario
		System.out.println("Enter channel probability p, 0 <= p <= p: ");
		String probability = sc.next();
		try {
			Double parsedProbability = Double.parseDouble(probability);
			if (parsedProbability.compareTo(0D) < 0 || parsedProbability.compareTo(1D) > 0) {
				System.out.println("Bad format, please try again");
				defineChannel();
			} else {
				channel.setProbability(BigDecimal.valueOf(parsedProbability));
			}
		} catch (NumberFormatException ex) {
			System.out.println("Bad format, please try again");
			defineChannel();
		}

	}

	public static void vectorScenario() throws IOException { // Vector scenario - encode, send through channel and decode
    	System.out.println("Enter a vector, accepted values are 0 and 1");
		String input = sc.next();
		List<Integer> vector = Utils.stringToIntegerList(input); // Convert string input to List<Integer>

		if(!vector.isEmpty()) { // Check if vector is not empty

			List<Integer> encodedVector = encoder.encode(vector); // Encode vector
			String encodedVectorString = encodedVector.stream()
					.map(String::valueOf)
					.collect(Collectors.joining(""));
			System.out.println("Encoded vector: " + encodedVectorString);

			List<Integer> sentVector = channel.sendVector(encodedVector); // Send vector through channel

			String decodedVectorString = decoder.decode(sentVector)// Decode vector
					.stream()
					.map(String::valueOf)
					.collect(Collectors.joining(""));
			System.out.println("Decoded vector: " + decodedVectorString);
		} else {
			vectorScenario();
		}

		greet();
	}

	public static void textScenario() throws IOException {
		System.out.println("Enter text to encode");
		String input = "";

		BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
		String line;

		while ((line = stdin.readLine()) != null && line.length()!= 0) {
			input += line + "\n";
		}

		List<Integer> vector = Utils.stringToBits(input);
		if(!vector.isEmpty()) {
			List<Integer> sentVectorNonEncoded = channel.sendText(vector);
			System.out.println("String passed through channel without coding/decoding: ");
			System.out.println(Utils.bitsToString(sentVectorNonEncoded));

			List<Integer> encodedVector = encoder.encode(vector);
			List<Integer> sentVector = channel.sendText(encodedVector); //send vector through channel
			List<Integer> decodedText = decoder.decode(sentVector);
			System.out.println("String passed through channel with coding/decoding: ");
			System.out.println(Utils.bitsToString(decodedText));
		} else {
			textScenario();
		}

		greet();
	}

	public static void imageScenario() throws IOException {
		greet();
	}

	public static void exitScenario() {
    	System.out.println("Goodbye");
		System.exit(0);
	}
}
