package com.jcf;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class QueueDemo {

	@SuppressWarnings({ "rawtypes", "unused" })
	public static void main(String[] args) {
		Queue<String> q=new LinkedList<>();
		q.offer("1");
		q.offer("2");
		q.offer("3");
		System.out.println(q.size());
		String s=null;
		while((s=q.poll())!=null){
			System.out.print(s+"\t");
		}
		
		ConcurrentLinkedQueue queue=null;
	}
}
