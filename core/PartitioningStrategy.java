package core;

public class PartitioningStrategy {

    public static int getPartitionForKey(String key, int partitionCount) {
        return Math.abs(key.hashCode() % partitionCount);
    }
}
