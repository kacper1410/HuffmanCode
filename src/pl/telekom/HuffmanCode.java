package pl.telekom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HuffmanCode {
    private ArrayList<Node> rootNodes;
    private final String message;

    public HuffmanCode(String message) {
        this.message = message;
        this.rootNodes = new ArrayList<Node>();
    }

    public void code() {
        calculateProbabilities();
//        for (Node n :
//                rootNodes) {
//            System.out.println("Value: " + (char) n.getValue() + ", probability: " + n.getProbability());
//        }
        constructTree();

    }

    private void calculateProbabilities() {
        boolean exist = false;
        for (char c : message.toCharArray()) {
            for (Node n : rootNodes) {
                if (n.getValue() == (byte) c) {
                    n.incrementProbability();
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                rootNodes.add(new Node((byte) c));
            }
            exist = false;
        }
    }

    private void constructTree() {
//        for (Node n :
//                rootNodes) {
//            System.out.println("Value: " + n.getValue() + ", probability: " + n.getProbability());
//        }
        while (rootNodes.size() > 1) {
            Collections.sort(rootNodes);
            Node firstNode = rootNodes.get(0);
            rootNodes.remove(0);
            Node secondNode = rootNodes.get(0);
            rootNodes.remove(0);
            Node root = new Node((firstNode.getProbability() + secondNode.getProbability()), (byte) 0);
            root.setLeftNode(firstNode);
            root.setRightNode(secondNode);
            rootNodes.add(root);
        }
        System.out.println(rootNodes.get(0).getProbability());
//
//        System.out.println(firstNode.getValue());
//        System.out.println(secondNode.getValue());
    }
}
