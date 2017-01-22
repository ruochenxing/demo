package com.jcf;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Spliterator;

public class ArrayListDemo {

	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) throws Exception{
		ArrayList<Integer> list=new ArrayList<>();
		for(int i=0;i<16;i++){
			list.add(i);
		}
		Class cla=Class.forName("java.util.ArrayList");
		Field field=cla.getDeclaredField("elementData");
		field.setAccessible(true);
		Object[]data=(Object[])field.get(list);
		System.out.println(data.length);
		LinkedList lit=null;
	}
	public static void test2(){
		List<String> list=new ArrayList<>();
		list.add("ab");
		list.add("ac");
		Spliterator<String> sp=list.spliterator();
		Spliterator<String> sp1=sp.trySplit();
		while(sp.tryAdvance(lang -> System.out.println(lang + "_12")));//如果存在没处理的数据，执行指定的代码
		while(sp1.tryAdvance(lang -> System.out.println(lang + "_11")));//如果存在没处理的数据，执行指定的代码
		//sp.forEachRemaining(System.out::println);//对每个数据执行指定的代码
	}
	public static void test(){
		List<String> list=new ArrayList<>();
		list.add("ab");
		list.add("abc");
		list.add("abd");
		List<String> sub=new ArrayList<>();
		sub.add("ab");
		sub.add("d");
		boolean result=list.retainAll(sub);//求交集 如果list发生了变化，则返回true
		System.out.println(result);
		list.stream().forEach(System.out::println);
		
		list.replaceAll(s->s+"1");
		list.forEach(System.out::print);
	}
}
