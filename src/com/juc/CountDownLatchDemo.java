package com.juc;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//同步计数器 多个线程和一个线程相互等待
public class CountDownLatchDemo {

	private final static CountDownLatch begin=new CountDownLatch(1);
	private final static CountDownLatch end=new CountDownLatch(5);

	public static void main(String[] args) {
		ExecutorService threadPool=Executors.newCachedThreadPool();
		for(int i=0;i<5;i++){
			final int index=i;
			Runnable run=new Runnable(){
				public void run(){
					try {
						begin.await();
						System.out.println("index="+index+"\t begin...");
						doSomething();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} finally{
						end.countDown();
						System.out.println("index="+index+"\t over...");
					}
				}
			};
			threadPool.execute(run);
		}
		begin.countDown();
		try{
			end.await();
		}catch(InterruptedException e){
			e.printStackTrace();
		}finally{
			System.out.println("over");
		}
		threadPool.shutdown();
	}
	public static void doSomething() throws InterruptedException{
		Thread.sleep((long)(Math.random()*10000));
	}
	
}
