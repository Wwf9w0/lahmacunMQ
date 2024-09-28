package core;

import model.Message;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Queue;

public class Partition {
    private int partitionId;
    private Node leaderNode;
    private Queue<Message> messageQueue;
    private List<Node> replicas;
    private long retentionTimeMillis;
    private long currentOffset;

    public Partition(int partitionId, Node leaderNode, long retentionTimeMillis) {
        this.partitionId = partitionId;
        this.leaderNode = leaderNode;
        this.messageQueue = new LinkedList<>();
        this.replicas = new ArrayList<>();
        this.retentionTimeMillis = retentionTimeMillis;
        this.currentOffset = 0;
    }

    public void addMessage(Message message, List<Node> replicasNodeList) {
        if (leaderNode.isLeader()) {
            leaderNode.addMessage(message);
            messageQueue.offer(message);
            replicas.addAll(replicasNodeList);
            addOtherNodes(message);
            currentOffset++;
            //  controlAndRemove(str);
            System.out.println("added message : " + message.getMessageContent() + " || offset : " + message.getOffset());
        } else {
            Optional<Node> leaderNode = findLeader();
            leaderNode.ifPresent(leader -> leader.addMessage(message));
            messageQueue.offer(message);
            addOtherNodes(message);
            currentOffset++;
            //      controlAndRemove(str);
        }
    }

    public Message getMessage(long offset) {
        for (Message message : messageQueue) {
            if (message.getOffset() == offset) {
                return message;
            }
        }
        return null;
    }

    public int getPartitionId() {
        return partitionId;
    }

    public Node getLeaderNode() {
        return leaderNode;
    }

    private void addOtherNodes(Message message) {
        for (Node node : replicas) {
            node.addMessage(message);
        }
    }

    private Optional<Node> findLeader() {
        for (Node node : replicas) {
            if (node.isLeader() && node.isAlive()) {
                return Optional.of(node);
            }
        }
        return Optional.empty();
    }

    private void controlAndRemove(String str) {
        Date now = new Date();
        if (leaderNode.isLeader() && leaderNode.isAlive()) {
            long diff = now.getTime() - retentionTimeMillis;
            if (diff > retentionTimeMillis) {
                Message message = new Message(str, currentOffset, retentionTimeMillis);
                Message m = messageQueue.stream()
                        .filter(q -> Objects.equals(q.getMessageContent(), message.getMessageContent()))
                        .findFirst()
                        .orElseThrow(NullPointerException::new);
                messageQueue.remove(m);
                removeReplicasPartitionsThisMessage(m);
                leaderNode.removeThisNode(m);
            }
        }
    }

    private void removeReplicasPartitionsThisMessage(Message message) {
        for (Node node : replicas) {
            node.removeThisNode(message);
        }
    }
}
