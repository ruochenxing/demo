package com.jcf;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class HashMapDemo {
	private final static Pattern emailer = Pattern.compile("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	@SuppressWarnings("unused")
	public static void main(String[] args) throws Exception{
		System.out.println(emailer.matcher("oschina-cn@163.com").matches());
		TreeMap<Integer,Student> maps=new TreeMap<Integer,Student>();
		HashMap<Integer,Student> students=getStudents();
		students.keySet().forEach(System.out::println);
		students.values().forEach(System.out::println);
		students.entrySet().forEach((Map.Entry<Integer,Student> entry)->{System.out.println("key="+entry.getKey()+"\tvalue="+entry.getValue());});
		System.out.println(students.getOrDefault(900, new Student("student_not_exist")));
	}
	@SuppressWarnings("rawtypes")
	public static void test() throws Exception{
		HashMap<Integer,Student> map=new HashMap<>(getStudents());
		Class clazz=Class.forName("java.util.HashMap");
		Field f0=clazz.getDeclaredField("threshold");
		f0.setAccessible(true);
		int threshold=(int)f0.get(map);
		
		Field f2=clazz.getDeclaredField("table");
		f2.setAccessible(true);
		Object[] table =(Object[])f2.get(map);
		System.out.println("size="+map.size()+"\t threshold="+threshold+"\t tablelength="+table.length);
		for(int i=0;i<40;i++){
			map.put(i, new Student("student"+i));
			try {
				clazz=Class.forName("java.util.HashMap");
				Field f1=clazz.getDeclaredField("threshold");
				f1.setAccessible(true);
				threshold=(int)f1.get(map);
				
				f2=clazz.getDeclaredField("table");
				f2.setAccessible(true);
				table =(Object[])f2.get(map);
				System.out.println("size="+map.size()+"\t threshold="+threshold+"\t tablelength="+table.length);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(map);
	}
	public static HashMap<Integer,Student> getStudents(){
		HashMap<Integer,Student> students=new HashMap<>();
		for(int i=1;i<=5;i++){
			students.put(i*100, new Student("old_student"+i));
		}
		return students;
	}
}
class Student{
	private String name;
	public Student(String name) {
		this.name = name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "Student [name=" + name + "]";
	}
}
