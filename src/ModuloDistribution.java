public class ModuloDistribution implements DistributionStrategy {

    @Override
    public int getNode(String key, int totalNodes) {
        int hash = Math.abs(key.hashCode());
        return hash % totalNodes;
    }
}
