# Expirable Map
Expirable Map with expire time setting written in Java

## Sample
```
Map<Integer, String> map = new CacheMap<Integer, String>(1000 * 5);
map.put(1, "value");
```
Then the `map.get(1)` will be expired after 5 minutes.
