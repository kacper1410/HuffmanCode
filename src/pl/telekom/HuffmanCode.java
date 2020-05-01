package pl.telekom;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HuffmanCode {
    private ArrayList<Node> rootNodes;
    private ArrayList<Element> dictionary;
    private ArrayList<Byte> compressed;
    private byte[] message;

    public HuffmanCode() {
        this.rootNodes = new ArrayList<Node>();
        this.dictionary = new ArrayList<Element>();
        this.compressed = new ArrayList<Byte>();
    }

    public void code(String pathToFile) throws IOException {

        message = Files.readAllBytes(Paths.get(pathToFile));

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
        byte currentByte = 0;
        int count = 0;
        for (byte byteOfMessage : message) {
            for (Element elem : dictionary) {
                if (elem.getValue() == byteOfMessage) {
                    for (char bit : elem.getKey().toCharArray()) {
                        currentByte *= 2;
                        if (bit == '1') {
                            currentByte += 1;
                        }
                        count++;
                        if (count == 8) {
                            compressed.add(currentByte);
                            count = 0;
                            currentByte = 0;
                        }
                    }
                    break;
                }
            }
        }
        compressed.add(0, (byte)count);
        if (count != 0) {
            while (count != 8) {
                currentByte *= 2;
                count++;
            }
            compressed.add(currentByte);
        }
        for (byte compressedByte : compressed) {
            System.out.println("Compressed byte: " + compressedByte);
        }

    }

    private void calculateProbabilities() {
        boolean exist = false;
        for (byte b : message) {
            for (Node n : rootNodes) {
                if (n.getValue() == b) {
                    n.incrementProbability();
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                rootNodes.add(new Node(b));
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
