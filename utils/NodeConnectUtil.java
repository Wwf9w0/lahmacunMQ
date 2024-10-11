package utils;

import core.Node;

import java.util.List;

public class NodeConnectUtil {

    public static void connectNodes(List<Node> nodes){
        for (Node node : nodes) {
            for (Node otherNode : nodes) {
                if (node != otherNode){
                    node.addToConnectedNodes(otherNode);
                }
            }
        }
    }
}
