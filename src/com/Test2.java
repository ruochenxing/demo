package com;

public class Test2 {

	public static void main(String[] args) {
		long result = 1;
		for (int i = 2; i <= 20; i++) {
			result *= i;
		}
		System.out.println(result+"\t"+Long.MAX_VALUE);
	}
}
