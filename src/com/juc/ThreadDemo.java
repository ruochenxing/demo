package com.juc;

import java.util.concurrent.CountDownLatch;

public class ThreadDemo {
	static final CountDownLatch latch=new CountDownLatch(2);
	public static void main(String[] args) throws Exception{
		final Thread thread1=new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("thread1 sleeping....");
				try {
					Thread.sleep(10*1000);
				} catch (InterruptedException e) {
					System.out.println("thread1 interrupted");
				}
				System.out.println("thread1 over");
				latch.countDown();
			}
		});
		thread1.start();
		Thread thread2=new Thread(new Runnable(){
			@Override
			public void run() {
				System.out.println("thread2 begin.....");
				try {
					Thread.sleep(2*1000);
					System.out.println("interrupt thread1");
					thread1.interrupt();
				} catch (InterruptedException e) {
				}
				latch.countDown();
			}
		});
		thread2.start();
		latch.await();
		System.out.println("end");
	}
}
