package core;

import model.Message;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Node {
    private String nodeName;

    private Queue<Message> messageQueue;
    private boolean isLeader;
    private boolean alive;
    private List<Node> connectedNodes;

    private long retentionTimeMillis;


    public Node(String nodeName, boolean isLeader, long retentionTimeMillis) {
        this.nodeName = nodeName;
        this.isLeader = isLeader;
        this.alive = true;
        this.connectedNodes = new ArrayList<>();
        this.messageQueue = new LinkedList<>();
        this.retentionTimeMillis = retentionTimeMillis;
    }

    public String getNodeName() {
        return nodeName;
    }

    public boolean isAlive() {
        return alive;
    }

    public boolean isLeader() {
        return isLeader;
    }

    public long getRetentionTimeMillis() {
        return retentionTimeMillis;
    }


    public void addMessage(Message message) {
            messageQueue.offer(message);
            addRemoteNodes(message);
    }

    public void replicasAddMessage(Message message){
        addRemoteNodes(message);

    }

    public void addRemoteNodes(Message message) {
        for (Node node : connectedNodes) {
            if (!node.messageQueue.contains(message)) {
                node.addMessage(message);
            }
        }
    }

    public void removeThisNode(Message message) {
        if (messageQueue.contains(message)) {
            messageQueue.remove(message);
            removeConnectedQueue(message);
        }
    }

    public void removeConnectedQueue(Message message) {
        for (Node node : connectedNodes) {
            if (node.messageQueue.contains(message)) {
                node.removeThisNode(message);
            }
        }
    }

    public void addToConnectedNodes(Node node) {
        connectedNodes.add(node);
    }
}
