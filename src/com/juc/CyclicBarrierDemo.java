package com.juc;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//栅栏 让多个线程相互等待某个状态再执行，可重入
public class CyclicBarrierDemo {

	private final static CyclicBarrier cyclicBarrier=new CyclicBarrier(3);
	
	public static void main(String[] args) {
		ExecutorService threadPool=Executors.newCachedThreadPool();
		for(int i=0;i<3;i++){
			final int index=i;
			threadPool.execute(()->{
				try{
					Thread.sleep((long)(Math.random()*10000));
					System.out.println("index="+index+" ready...");
					cyclicBarrier.await();
				}catch(InterruptedException |BrokenBarrierException e){
					e.printStackTrace();
				}
				System.out.println("index="+index+" go...");
				try{
					Thread.sleep((long)(Math.random()*10000));
					System.out.println("index="+index+" arriving...");
					cyclicBarrier.await();
				}catch(InterruptedException |BrokenBarrierException e){
					e.printStackTrace();
				}
			});
		}
		threadPool.shutdown();
		while(!threadPool.isTerminated()){
		}
		System.out.println("finished");
	}
}
