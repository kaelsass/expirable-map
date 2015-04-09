package cn.com.kaelsass.sample;

import java.util.Map;

import cn.com.kaelsass.CacheMap;

public class Sample {
	public static void main(String[] args)
	{
		Map<Integer, String> map = new CacheMap<Integer, String>(1000 * 5);
		map.put(1, "value");
		System.out.println("value exist: " + map.containsKey(1));
		try {
			Thread.sleep(1000 * 5);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println("value exist after expire time: " + map.containsKey(1));
	}
}
