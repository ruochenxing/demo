package com;

import java.util.Optional;

/**
 * FizzBuzz problem solution in Java 8. It's a classical problem to filter
 * programmers who can't program. Now you can use this to check whether your
 * Java candidate knows programming with Java 8 Streams or not.
 *
 * Problem : Write a method in Java which will return "Fizz" if the number is
 * dividable by 3 "Buzz" if the number is dividable by 5 "FizzBuzz" if the
 * number is dividable by 15 the same number if no other requirement is
 * fulfilled.
 *
 * @author Javin Paul
 */
public class FizzBuzzJava8 {

	public static void main(String args[]) {
		System.out.println("FizzBuzz using simple Java : " + fizzBuzz(3));
		System.out.println("FizzBuzz solution using Java 8  : " + fizzBuzzInJava8(15));
	}

	/**
	 * Simple Java solution of FizzBuzz Problem
	 *
	 * @param number
	 * @return Fizz if number divisible by 3, Buzz if number divisible by 5
	 *         FizzBuzz if divisible by both 3 and 5, or else the same number
	 */
	public static String fizzBuzz(int number) {
		if (number % 15 == 0) {
			return "FizzBuzz";
		} else if (number % 3 == 0) {
			return "Fizz";
		} else if (number % 5 == 0) {
			return "Buzz";
		}
		return Integer.toString(number);
	}

	/**
	 * FizzBuzz Solution using Java 8 Optional, map and Stream map() function is
	 * really useful here.
	 *
	 * @param number
	 * @return Fizz, Buzz, FizzBuzz or the number itself
	 */
	public static String fizzBuzzInJava8(int number) {
		String result = Optional.of(number).map(n -> (n % 3 == 0 ? "Fizz" : "") + (n % 5 == 0 ? "Buzz" : "")).get();
		return result.isEmpty() ? Integer.toString(number) : result;
	}

	/*
	 * Another Java 8 solution, this time its little bit more expressive for
	 * Java 8 newbie.
	 */
	public static String fizzBuzzSolutionJava8(int input) {
		return Optional.of(input).map(i -> {
			if (i % (3 * 5) == 0) {
				return "FizzBuzz";
			} else if (i % 3 == 0) {
				return "Fizz";
			} else if (i % 5 == 0) {
				return "Buzz";
			} else {
				return Integer.toString(i);
			}
		}).get();
	}

}
