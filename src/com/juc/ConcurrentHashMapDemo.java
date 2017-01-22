package com.juc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapDemo {

	public static void main(String[] args) {
		Map<String, String> cm = new ConcurrentHashMap<String, String>();
	    for (int i = 0; i < 14; i++) {
	        cm.put("key_" + i, "huaizuo_" + i);
	    }
	}
}
