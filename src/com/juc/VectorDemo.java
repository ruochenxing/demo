package com.juc;

import java.util.Enumeration;
import java.util.Vector;

public class VectorDemo {

	public static void main(String args[]) {
		Vector<Object> v = new Vector<>(3, 2);
		System.out.println("Initial size: " + v.size());
		System.out.println("Initial capacity: " + v.capacity());
		v.addElement(new Integer(1));
		System.out.println("First element: " + (Integer) v.firstElement());
		System.out.println("Last element: " + (Integer) v.lastElement());
		if (v.contains(new Integer(3)))
			System.out.println("Vector contains 3.");
		Enumeration<Object> vEnum = v.elements();
		System.out.println("\nElements in vector:");
		while (vEnum.hasMoreElements())
			System.out.print(vEnum.nextElement() + " ");
	}
}
