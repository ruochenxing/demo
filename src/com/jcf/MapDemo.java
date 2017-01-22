package com.jcf;

public class MapDemo {
	final static int MAXIMUM_CAPACITY = 1 << 30;//1*2^30
	static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
	public static void main(String[] args) {
		int s=5;
		float ft = ((float)s / 0.75f) + 1.0F;
        int t = ((ft < (float)MAXIMUM_CAPACITY) ?
                 (int)ft : MAXIMUM_CAPACITY);
        int threshold=0;
        if (t > threshold)
            threshold = tableSizeFor(t);
		System.out.println(threshold);
		System.out.println("==========");
		System.out.println((int)((float)5/0.75+1.0f));
		System.out.println(tableSizeFor(12));
		print1(0,-100>>>0);
		print1(0,-100>>>16);
		print1(0,(-100)^(-100>>>16));
	}
	public static void test1(){
		Float f=new Float(0.0f/0.0f);
		System.out.println(f);//NaN not a number
		Float f1=new Float(1.0/0.0);
		System.out.println(f1); //Infinity 正无穷大
		Float f2=new Float(-1.0/0.0);
		System.out.println(f2);//-Infinity 负无穷大
		// f>f1>f2
		System.out.println(Float.NaN==Float.NaN);							//false
		
		System.out.println(Float.compare(Float.NaN, Float.NaN));			//0
		System.out.println(Float.isNaN(Float.NaN));							//true
	}
	public static void test(){
		int num=12345678;
		for(int i=0;i<32;i++){
							 //00000000101111000110000101001110
			print1(i,num>>i);//00000000000000000000000010111100
		}
		for(int i=0;i<32;i++){
			print1(i,num>>>i);
		}
		num=-10;
		for(int i=0;i<32;i++){
			print1(i,num>>i);
		}
		for(int i=0;i<32;i++){
			print1(i,num>>>i);
		}
	}
	public static void print(int count,int i){
		System.out.println(count+","+i+"="+Integer.toBinaryString(i));
	}
	public static void print1(int count,int num){
		System.out.print(count+",");
		for (int i=0; i<32; i++) {
			int t=(num&0x80000000>>>i)>>>(31-i);
			System.out.print(t);
		}
		System.out.println(","+num);
	}
}
