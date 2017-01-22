package com;

public class ClassDemo {

}

abstract class A {
	public abstract void method();

	public void method2() {
	}
}

interface B {
	public void method();

	public static void method1() {
	};

	public abstract void method2();

	default void foo(){};
}
