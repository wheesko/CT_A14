package com.VU;

import java.util.*;
import java.math.BigDecimal;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

	private static Channel channel = new Channel();
	private static Encoder encoder = new Encoder();
	private static Decoder decoder = new Decoder();
	private static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
		greet();
    }

    public static void greet() { //Main greeting scenario
    	System.out.println();
		System.out.println("Enter scenario by choosing a number between 1-4: ");
		System.out.println("1: Vector");
		System.out.println("2: Text");
		System.out.println("3: Image");
		System.out.println("4: Exit program");
		getScenario();
	}

    public static void getScenario() { //Method for determining what scenario to pick
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

	public static void vectorScenario() { // Vector scenario - encode, send through channel and decode
    	System.out.println("Enter a vector, accepted values are 0 and 1");
		String input = sc.next();
		List<Integer> vector = Utils.stringToIntegerList(input);

		if(!vector.isEmpty()) { //Check if is valid binary vector (only 0 and 1 allowed)
			List<Integer> encodedVector = encoder.encode(vector);
			System.out.println("Encoded vector: " + encodedVector.stream().map(String::valueOf).collect(Collectors.joining("")));
			channel.sendVector(encodedVector); //send vector through channel
		} else {
			vectorScenario();
		}

		greet();
	}

	public static void textScenario() {
    	greet();
	}

	public static void imageScenario() {
		greet();
	}

	public static void exitScenario() {
    	System.out.println("Goodbye");
		System.exit(0);
	}
}
