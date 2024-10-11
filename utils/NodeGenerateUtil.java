package utils;

import core.Node;

import java.util.ArrayList;
import java.util.List;

public class NodeGenerateUtil {

    public static List<Node> generateNodes(int size_n, boolean isLeaderFirst, long retentionTimeMillis) {
        List<Node> nodes = new ArrayList<>();
        Node node;
        for (int i = 0; i < size_n; i++) {
            String nodeName = "Node " + i;
            if (i == 0) {
                node = new Node(nodeName, true, retentionTimeMillis);
            }
            node = new Node(nodeName, false, retentionTimeMillis);
            nodes.add(node);
        }
        return nodes;
    }
}
