import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Topic {
    private String topicName;
    private Map<Integer, Partition> partitions;

    public Topic(String topicName, int partitionCount, Node leaderNode, long retentionTimeMillis) {
        this.topicName = topicName;
        this.partitions = new HashMap<>();

        for (int i = 0; i < partitionCount; i++) {
            partitions.put(i, new Partition(i, leaderNode, retentionTimeMillis));
        }
    }

    public void addMessage(Message message, int partitionId) {
        Partition currentPartition = partitions.get(partitionId);
        List<Partition> replicasNodeList = new ArrayList<>();
        for (Map.Entry<Integer, Partition> entry : partitions.entrySet()) {
            Partition mapPartition = entry.getValue();
            if (currentPartition.getPartitionId() != mapPartition.getPartitionId()) {
                replicasNodeList.add(mapPartition);
            }
        }
        if (currentPartition != null) {
            List<Node> nodes = replicasNodeList.stream().map(Partition::getLeaderNode).collect(Collectors.toList());
            currentPartition.addMessage(message, nodes);
        } else {
            System.out.println("Invalid partition ID: " + partitionId);
        }
    }

    public Message getMessage(int partitionId, long offset) {
        Partition currentPartition = partitions.get(partitionId);
        if (currentPartition != null) {
            return currentPartition.getMessage(offset);
        } else {
            return null;
        }
    }
}
