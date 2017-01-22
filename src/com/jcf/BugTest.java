package com.jcf;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class BaseClass {
}

class SubClass extends BaseClass {
}

/**
 * 通过test2和test3可以看出，如果我们有1个List<String> stringList对象，当我么调用Object[] objectArray
 * = stringList.toArray();的时候， objectArray 并不一定能够放置Object对象。
 * 
 * 1.此处之所以返回String[]是因为java.util.Arrays$ArrayList中用来保存数据的是一个范型数组
 * 2.此处之所以返回Object[]是因为java.util.ArrayList 中用来保存数据的是一个Object[]数组 即：
 * Arrays.ArrayList.toArray() does not return Object[] always
 * Arrays.asList(x).toArray().getClass() should be Object[].class
 * 
 * @see http://bugs.java.com/bugdatabase/view_bug.do?bug_id=6260652
 * 
```	
	public class ToArray {
		public static void main(String[] args) {
			List l = Arrays.asList(args);
			System.out.println(l.toArray());
			System.out.println(l.toArray(new Object[0]));
		}
	}
```
 */
public class BugTest {
	public static void main(String[] args) {
		test2();
		// test3();
		// Object o=new String("asdf");
	}

	public static void test1() {
		SubClass[] subArray = { new SubClass(), new SubClass() };
		System.out.println(subArray.getClass());// 返回SubClass[]
		BaseClass[] baseArray = subArray;
		System.out.println(baseArray.getClass());// class [Lcom.SubClass;

		// java.lang.ArrayStoreException
		baseArray[0] = new BaseClass();
	}

	public static void test2() {
		List<String> list = Arrays.asList("abc");//返回的是java.util.Arrays$ArrayList
		System.out.println(list.getClass()); 
		Object[] objArray = list.toArray();// 返回String[] see 1
		System.out.println(objArray.getClass());// class [Ljava.lang.String;

		// cause ArrayStoreException
		objArray[0] = new Object();// 如同把一个Object赋值String
	}

	public static void test3() {
		List<String> dataList = new ArrayList<String>();
		dataList.add("one");
		dataList.add("two");

		Object[] listToArray = dataList.toArray();// 返回Object[] see 2
		System.out.println(listToArray.getClass());// class [Ljava.lang.Object;
		listToArray[0] = "";
		listToArray[0] = 123;
		listToArray[0] = new Object();

	}
}
