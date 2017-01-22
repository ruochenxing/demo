package com.juc;

import java.util.Enumeration;
import java.util.Hashtable;

public class HashTableDemo {
	public static void main(String args[]) {
		Hashtable<String, Double> balance = new Hashtable<>();
		Enumeration<String> names;
		String str;
		double bal;
		balance.put("Zara", new Double(3434.34));
		names = balance.keys();
		while (names.hasMoreElements()) {
			str = (String) names.nextElement();
			System.out.println(str + ": " + balance.get(str));
		}
		bal = ((Double) balance.get("Zara")).doubleValue();
		balance.put("Zara", new Double(bal + 1000));
		System.out.println("Zara's new balance: " + balance.get("Zara"));
	}
}
