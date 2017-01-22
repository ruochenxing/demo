package com.jcf;

public class TestSynchronized {
    public static void main(String[] args) {
        Sy sy = new Sy(0);
        Sy sy2 = new Sy(1);
        sy.start();
        sy2.start();
    }
}

class Sy extends Thread {
    private int flag ;

    static Object x1 = new Object();
    static Object x2 = new Object();

    public Sy(int flag) {
        this.flag = flag;
    }
    @Override
    public void run() {
        try {
            if (flag == 0) {
                synchronized (x1) {
                    Thread.sleep(1000);
                    synchronized (x2) {
                    }
                }
            }
            if(flag == 1) {
                synchronized (x2) {
                    Thread.sleep(1000);
                    synchronized (x1) {
                    }
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}