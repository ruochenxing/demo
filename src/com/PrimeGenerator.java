package com;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class PrimeGenerator implements Runnable {
	private final List<BigInteger> primes = new ArrayList<>();
	private volatile boolean cancelled;

	public void run() {
		BigInteger p = BigInteger.ONE;
		while (!cancelled) {
			p = p.nextProbablePrime();
			synchronized (primes) {
				primes.add(p);
			}
		}
	}

	public void cancel() {
		cancelled = true;
	}

	public synchronized List<BigInteger> get() {
		return new ArrayList<BigInteger>(primes);
	}

	public static void main(String[] args) throws Exception {
		PrimeGenerator generator = new PrimeGenerator();
		new Thread(generator).start();
		try {
			Thread.sleep(100);
		} finally {
			generator.cancel();
		}
		List<BigInteger> results = generator.get();
		for (BigInteger i : results) {
			System.out.println(i);
		}
	}
}
