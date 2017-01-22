package com.jcf;

public class FloatDemo {

	public static void main(String[] args) {
		for(int i=0;i<args.length;i++){
			System.out.println("args "+i+" = "+args[i]);
		}
		System.out.println(Runtime.getRuntime().maxMemory()/1000/1000+"M");
	}
	public static void test(){
		Float f=Float.MAX_VALUE;
		System.out.println(f);
		System.out.println(Integer.toBinaryString(Float.floatToRawIntBits(f)));
		System.out.println(Float.intBitsToFloat(0x7f7fffff));
		// s     e      m
		// 0 11111110 11111111111111111111111
		// 1.fffffe P+127;
		// 1.e ==> 1.111
 		// 1*2^(254-127)*(1+0.5+0.25+0.125+...)=2^127*(2-2^-23)=1.99999988079*2^127<2^128
		System.out.println("==============================");
		int intBits = Float.floatToIntBits(5.5f);
		System.out.println(Integer.toBinaryString(intBits));  
		System.out.println(Integer.toBinaryString(Float.floatToRawIntBits(5.5f)));
	}
}
