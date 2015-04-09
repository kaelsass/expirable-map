package cn.com.kaelsass;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CacheMap<K, V> extends AbstractMap<K, V> {

	private long expireTime;
	private Map<K, CacheValue<V>> map;
	public CacheMap(long expireTime)
	{
		if(expireTime < 0)
			expireTime = 0;
		this.expireTime = expireTime;
		map = new HashMap<K, CacheValue<V>>();
	}
	
	@Override
	public boolean containsKey(Object key) {
		if(!map.containsKey(key))
			return false;
		if(isExpired(map.get(key)))
		{
			map.remove(key);
			return false;
		}
		return true;
	}

	@Override
	public V get(Object key) {
		CacheValue<V> item = map.get(key);
		if(item == null)
			return null;
		if(isExpired(item))
		{
			map.remove(key);
			return null;
		}
		return item.getValue();
	}
	
	@Override
	public V put(K key, V value) {
		map.put(key, new CacheValue<V>(value));
		return value;
	}
	
	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> entrySet = new HashSet<Entry<K,V>>();
		Set<Entry<K, CacheValue<V>>> wrapEntrySet = map.entrySet();
		for(Entry<K, CacheValue<V>> item : wrapEntrySet)
		{
			entrySet.add(new SimpleEntry<K, V>(item.getKey(), item.getValue().getValue()));
		}
		return entrySet;
	}
	
	
	private boolean isExpired(CacheValue<V> value) {
		if(System.currentTimeMillis() - value.getCreateTime() >= expireTime)
			return true;
		return false;
	}
	
	private class CacheValue<T>
	{
		private long createTime;
		private T value;
		public CacheValue(T value)
		{
			this.value = value;
			createTime = System.currentTimeMillis();
		}
		
		public long getCreateTime() {
			return createTime;
		}
		
		public T getValue() {
			return value;
		}
	}
	

}
