package com;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 红包额度随机，在0.01和(剩余平均值*2)之间。
 * 例如：发100块钱，总共10个红包，那么平均值是10块钱一个，那么发出来的红包的额度在0.01元～20元之间波动。
 * 
 * 每抢到一个红包，就cas更新剩余金额和红包个数。
 */
public class RedPacket {
	public static LeftMoneyPackage leftMoneyPackage = new LeftMoneyPackage();
	public static Map<Integer, List<Double>> map = new HashMap<Integer, List<Double>>();

	public static void main(String[] args) {
		test1();
	}

	public static void test0() {
		double x = 1.111;
		System.out.println(x);
		System.out.println((double) Math.round(x));// 精确到整数
		System.out.println((double) Math.round(x * 100) / 100);// 精确到两位小数
	}

	public static void test1() {
		for (int i = 0; i < 100000; i++) {
			init();
			begin();
		}
		for (Map.Entry<Integer, List<Double>> entry : map.entrySet()) {
			int index = entry.getKey();
			List<Double> list = entry.getValue();
			double average = list.stream().collect(Collectors.averagingDouble(Double::doubleValue));
			System.out.println(index + ":" + (double) Math.round(average * 1000) / 1000);
		}
	}

	public static void begin() {
		int size = leftMoneyPackage.remainSize;
		double results[] = new double[size];
		for (int i = 0; i < size; i++) {
			results[i] = getRandomMoney(leftMoneyPackage);
			if (map.get(Integer.valueOf(i)) == null) {
				map.put(Integer.valueOf(i), new ArrayList<Double>());
			}
			map.get(Integer.valueOf(i)).add(results[i]);
		}
		// System.out.println(Arrays.toString(results));
		// double total = 0.0;
		// for (double i : results) {
		// total += i;
		// }
		// System.out.println(total);
	}

	static void init() {
		leftMoneyPackage.remainSize = 20;
		leftMoneyPackage.remainMoney = 200;
	}

	public static double getRandomMoney(LeftMoneyPackage leftMoneyPackage) {
		if (leftMoneyPackage.remainSize == 1) {
			leftMoneyPackage.remainSize--;
			return (double) Math.round(leftMoneyPackage.remainMoney * 100) / 100; // round四舍五入
			// return leftMoneyPackage.remainMoney;
		}
		Random random = new Random();
		double min = 0.01;
		double max = leftMoneyPackage.remainMoney / leftMoneyPackage.remainSize * 2;
		double money = random.nextDouble() * max; // nextDouble [0,1)
		money = money < min ? min : money;
		money = (double) Math.floor(money * 100) / 100; // 向下取整
		leftMoneyPackage.remainSize--;
		leftMoneyPackage.remainMoney -= money;
		return money;
	}

	static class LeftMoneyPackage {
		int remainSize;// 剩余的红包数量
		double remainMoney;// 剩余的钱
	}
}
