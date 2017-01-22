package com.juc;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

//信号量，控制对资源的访问线程数
public class SemaphoreTest {

	public static void main(String[] args) {
		ExecutorService threadPool = Executors.newCachedThreadPool();
		final Semaphore sem = new Semaphore(5);
		for (int index = 0; index < 10; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						sem.acquire();
						System.out.println("Accessing:" + NO);
						doSomething();
						sem.release();
						System.out.println("==========Out:" + NO);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
			threadPool.execute(run);
		}
		threadPool.shutdown();
	}

	public static void doSomething() throws InterruptedException {
		Thread.sleep((long) (Math.random() * 10000));
	}
}
