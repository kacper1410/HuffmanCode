package pl.telekom;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HuffmanCode {
    private ArrayList<Node> rootNodes;
    private ArrayList<Element> dictionary;
    private ArrayList<Byte> compressed;
    private final String message;

    public HuffmanCode(String message) {
        this.message = message;
        this.rootNodes = new ArrayList<Node>();
        this.dictionary = new ArrayList<Element>();
        this.compressed = new ArrayList<Byte>();
    }

    public void code() {
        calculateProbabilities();
        Collections.sort(rootNodes);
//        for (Node n :
//                rootNodes) {
//            System.out.println("Value: " + (char) n.getValue() + ", probability: " + n.getProbability());
//        }
        constructTree();

        createDictionary(rootNodes.get(0), "");

//        for (Element e :
//                dictionary) {
//            System.out.println("Value: " + (char) e.getValue() + "  Key: " + e.getKey());
//        }
        byte b = 0;
        int count = 0;
        for (char c : message.toCharArray()) {
            for (Element e : dictionary) {
                if (e.getValue() == c) {
                    for (char bit : e.getKey().toCharArray()) {
                        b *= 2;
                        if (bit == '1') {
                           b += 1;
                        }
                        count++;
                        if (count == 8) {
                            compressed.add(b);
                            count = 0;
                            b = 0;
                        }
                    }
                    break;
                }
            }
        }
        if (count != 0) {
            compressed.add(0, (byte)count);
            while (count != 8) {
                b *= 2;
                count++;
            }
            compressed.add(b);
        }
        for (byte by :
                compressed) {
            System.out.println("Compressed byte: " + by);
        }

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
//        System.out.println(rootNodes.get(0).getProbability());
//
//        System.out.println(firstNode.getValue());
//        System.out.println(secondNode.getValue());
    }

    private void createDictionary(Node node, String key) {
        if (node.getLeftNode() == null) {
            if (node.getRightNode() == null) {
                dictionary.add(new Element(node.getValue(), key));
                return;
            }
            createDictionary(node.getRightNode(), key + "1");

        }
        if (node.getRightNode() == null) {
            createDictionary(node.getLeftNode(), key + "0");
        }
        createDictionary(node.getLeftNode(), key + "0");
        createDictionary(node.getRightNode(), key + "1");
    }


}
