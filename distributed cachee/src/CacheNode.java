import java.util.HashMap;
import java.util.Map;

public class CacheNode {

    private int nodeId;
    private int capacity;
    private Map<String, String> cache;
    private EvictionPolicy evictionPolicy;

    public CacheNode(int nodeId, int capacity, EvictionPolicy evictionPolicy) {
        this.nodeId = nodeId;
        this.capacity = capacity;
        this.cache = new HashMap<>();
        this.evictionPolicy = evictionPolicy;
    }

    public String get(String key) {
        if (!cache.containsKey(key)) {
            return null;
        }
        evictionPolicy.keyAccessed(key);
        return cache.get(key);
    }

    public void put(String key, String value) {
        if (cache.containsKey(key)) {
            cache.put(key, value);
            evictionPolicy.keyAccessed(key);
            return;
        }

        if (cache.size() >= capacity) {
            String evictedKey = evictionPolicy.evict();
            if (evictedKey != null) {
                cache.remove(evictedKey);
            }
        }

        cache.put(key, value);
        evictionPolicy.keyAccessed(key);
    }

    public boolean containsKey(String key) {
        return cache.containsKey(key);
    }

    public int getNodeId() {
        return nodeId;
    }
}
