public class Main {
    public static void main(String[] args) {
        Database db = new InMemoryDatabase();
        db.put("user1", "Alice");
        db.put("user2", "Bob");
        db.put("user3", "Charlie");
        db.put("user4", "David");
        db.put("user5", "Eve");

        DistributionStrategy strategy = new ModuloDistribution();
        EvictionPolicy evictionSample = new LRUEvictionPolicy();

        int numberOfNodes = 3;
        int capacityPerNode = 2;

        DistributedCache cache = new DistributedCache(numberOfNodes, capacityPerNode, strategy, evictionSample, db);

        System.out.println("=== Testing GET (Cache Miss -> fetches from DB) ===");
        System.out.println("user1 = " + cache.get("user1"));
        System.out.println("user2 = " + cache.get("user2"));
        System.out.println("user3 = " + cache.get("user3"));
        System.out.println();

        System.out.println("=== Testing GET (Cache Hit) ===");
        System.out.println("user1 = " + cache.get("user1"));
        System.out.println("user2 = " + cache.get("user2"));
        System.out.println();

        System.out.println("=== Testing PUT ===");
        cache.put("user6", "Frank");
        cache.put("user7", "Grace");
        System.out.println();

        System.out.println("=== Testing Eviction (capacity = 2 per node) ===");
        cache.put("user8", "Heidi");
        System.out.println("user8 = " + cache.get("user8"));
        System.out.println();

        System.out.println("=== Verifying evicted keys fall back to DB ===");
        System.out.println("user1 = " + cache.get("user1"));
        System.out.println("user4 = " + cache.get("user4"));
    }
}
