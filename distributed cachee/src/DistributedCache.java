import java.util.ArrayList;
import java.util.List;

public class DistributedCache {

    private List<CacheNode> nodes;
    private DistributionStrategy strategy;
    private Database database;

    public DistributedCache(int numberOfNodes, int capacityPerNode, DistributionStrategy strategy,
                            EvictionPolicy evictionPolicyFactory, Database database) {
        this.strategy = strategy;
        this.database = database;
        this.nodes = new ArrayList<>();

        for (int i = 0; i < numberOfNodes; i++) {
            nodes.add(new CacheNode(i, capacityPerNode, createEvictionPolicy(evictionPolicyFactory)));
        }
    }

    private EvictionPolicy createEvictionPolicy(EvictionPolicy sample) {
        if (sample instanceof LRUEvictionPolicy) {
            return new LRUEvictionPolicy();
        }
        return new LRUEvictionPolicy();
    }

    public String get(String key) {
        int nodeIndex = strategy.getNode(key, nodes.size());
        CacheNode node = nodes.get(nodeIndex);

        String value = node.get(key);
        if (value != null) {
            System.out.println("Cache HIT for key '" + key + "' on Node " + nodeIndex);
            return value;
        }

        System.out.println("Cache MISS for key '" + key + "' on Node " + nodeIndex + ". Fetching from DB...");
        value = database.get(key);
        if (value != null) {
            node.put(key, value);
        }
        return value;
    }

    public void put(String key, String value) {
        int nodeIndex = strategy.getNode(key, nodes.size());
        CacheNode node = nodes.get(nodeIndex);
        node.put(key, value);
        database.put(key, value);
        System.out.println("PUT key '" + key + "' -> Node " + nodeIndex);
    }
}
