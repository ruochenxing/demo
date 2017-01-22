package com.jcf;

import java.lang.reflect.Method;

public class SetDemo {

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws ClassNotFoundException {
		Class clazz=Class.forName("java.util.HashSet");
		Method[] methods=clazz.getDeclaredMethods();
		for(Method m:methods){
			System.out.print(m.getName()+"\t");
		}
	}
}
