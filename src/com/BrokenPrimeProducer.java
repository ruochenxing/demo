package com;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class BrokenPrimeProducer extends Thread{
    private final BlockingQueue<BigInteger> queue;
    private volatile boolean cancelled=false;
    public BrokenPrimeProducer(BlockingQueue<BigInteger> queue){
        this.queue=queue;
    }
    public void run(){
        try{
            BigInteger p=BigInteger.ONE;
            while(!cancelled){
                queue.put(p=p.nextProbablePrime());
            }
        }catch(InterruptedException e){}
    }
    public void cancel(){
        cancelled=true;
    }
    public static void main(String []args) throws Exception{
        BlockingQueue<BigInteger> primes=new LinkedBlockingQueue<BigInteger>(10);
        BrokenPrimeProducer producer=new BrokenPrimeProducer(primes);
        producer.start();
        int count=0;
        try{
            while(count<10){
                count++;
                Thread.sleep(1000);
                System.out.println(primes.take());
            }
        }finally{
            producer.cancel();
        }
        System.out.println("over..");
    }
}
