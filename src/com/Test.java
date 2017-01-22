package com;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.TreeMap;
import java.util.Vector;
import java.util.stream.Collectors;

class Parent {
	int x = 1;
}

class Child extends Parent {
	protected int x;

	public Child() {
		System.out.println(super.x + "," + x);
	}
}

public class Test {
	public static <T> void fromArrayToCollection(T[] a, Collection<T> c) {
		for (T t : a) {
			c.add(t);
			// c.add(new Object()); //编译错误
		}
	}

	public static void main(String[] args) {
		// Child c=new Child();
		// System.out.println(c.x);
		// Arrays.asList(System.getProperty("sun.boot.class.path").split(":")).stream().forEach(System.out::println);
		// System.out.println("java.ext.dirs:" +
		// System.getProperty("java.ext.dirs"));
		// System.out.println("java.class.path:"
		// +System.getProperty("java.class.path"));
		// ClassLoader cl = Thread.currentThread().getContextClassLoader();//等同于
		// ClassLoader.getSystemClassLoader()
		// System.out.println("getContextClassLoader:" +cl.toString());
		// System.out.println("getContextClassLoader.parent:"
		// +cl.getParent().toString());
		// System.out.println("getContextClassLoader.parent2:"
		// +cl.getParent().getParent());
		List<Integer> ints = new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4, 5));
		System.out.println(ints.stream().map(x -> x * 2).collect(Collectors.toList()));
	}

	public static void test1() {
		int i = 20;// 10100
		int j = 30;// 11110
		int k = 30;// 11110
		i = i << 3;// 10100000 -> 160
		j = j >> 70;// 11110 -> 0
		System.out.println(70 % 32);// 6
		k = k >> 6;
		System.out.println(i + "," + j + "," + k);// 160,0,0
	}

	public static void test0() {
		String str = new StringBuilder("fu").append("ck").toString();
		System.out.println(str.intern() == str);// true
		String str1 = new StringBuilder("ja").append("va").toString();
		System.out.println(str1.intern() == str1);// false

		TreeMap<Integer, String> treeMap = new TreeMap<>();
		for (int i = 0; i < 100; i++)
			treeMap.put(i, "asdfasdf");
		treeMap.remove(1);
		treeMap.remove(2);
		treeMap.remove(3);
		treeMap.remove(4);
		treeMap.remove(5);
	}

	public static void test() {
		Arrays.asList(fecthAllTimeZoneIds()).stream().forEach(System.out::println);
		Date date = new Date(1477975027000L);
		TimeZone timeZone = TimeZone.getTimeZone("Asia/Shanghai");
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeZone(timeZone);
		calendar.setTime(date);
		System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss Z").format(date));
		System.out.println(similar("", ""));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String[] fecthAllTimeZoneIds() {
		Vector v = new Vector();
		String[] ids = TimeZone.getAvailableIDs();
		for (int i = 0; i < ids.length; i++) {
			v.add(ids[i]);
		}
		java.util.Collections.sort(v, String.CASE_INSENSITIVE_ORDER);
		v.copyInto(ids);
		v = null;
		return ids;
	}

	/**
	 * Levenshtein Distance 编辑距离算法计算相似度
	 */
	public static Double similar(String str1, String str2) {
		int matrix[][];
		int a = str1.length();
		int b = str2.length();
		int max = a < b ? b : a;
		int temp;
		if (a == 0 || b == 0) {
			return (double) 0;
		}
		matrix = new int[a + 1][b + 1];
		for (int i = 0; i <= a; i++) {
			matrix[i][0] = i;
		}
		for (int j = 0; j <= b; j++) {
			matrix[0][j] = j;
		}
		for (int i = 1; i <= a; i++) {
			for (int j = 1; j <= b; j++) {
				if (str2.charAt(j - 1) == str1.charAt(i - 1)) {
					temp = 0;
				} else {
					temp = 1;
				}
				matrix[i][j] = min(matrix[i - 1][j] + 1, matrix[i][j - 1] + 1, matrix[i - 1][j - 1] + temp);
			}
		}
		return 1 - (double) matrix[a][b] / (double) max;
	}

	public static int min(int one, int two, int three) {
		int min = one;
		if (two < min) {
			min = two;
		}
		if (three < min) {
			min = three;
		}
		return min;
	}
}
