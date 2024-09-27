import java.util.ArrayList;
import java.util.List;

public class Node {
    private String nodeName;
    private boolean isLeader;
    private boolean alive;
    private List<Node> connectedNodes;

    public Node(String nodeName, boolean isLeader) {
        this.nodeName = nodeName;
        this.isLeader = isLeader;
        this.alive = true;
        this.connectedNodes = new ArrayList<>();
    }

    public boolean isLeader() {
        return isLeader;
    }

    public void setLeader(boolean leader) {
        isLeader = leader;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void handleLeaderElection(List<Node> nodes) {
        if (!alive && isLeader) {
            System.out.println(nodeName + "leader node unsuccessfully.");
        }
    }

    private void electNewLeader(List<Node> nodes) {
        for (Node node : nodes) {
            if (node.isAlive() && !node.isLeader()) {
                node.setLeader(true);
                this.setLeader(false);
                System.out.println(node.nodeName + "is the new leader.");
                break;
            }
        }
    }

    public void addNode(Node node) {
        connectedNodes.add(node);
    }
}
