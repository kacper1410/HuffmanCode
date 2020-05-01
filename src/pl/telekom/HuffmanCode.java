package pl.telekom;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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

    public void code(String toCompress, String compressedFile, String dictionaryFile) throws IOException {
        rootNodes.clear();
        dictionary.clear();
        compressed.clear();


        message = Files.readAllBytes(Paths.get(toCompress));

        calculateProbabilities();

//        for (Node n :
//                rootNodes) {
//            System.out.println("Value: " + (char) n.getValue() + ", probability: " + n.getProbability());
//        }

        constructTree();

        createDictionary(rootNodes.get(0), "");

//        for (Element e : dictionary) {
//            System.out.println("Value: " + e.getValue() + "  Key: " + e.getKey());
//        }

        compress();

//        for (byte compressedByte : compressed) {
//            System.out.println("Compressed byte: " + compressedByte);
//        }

//        System.out.println((compressed));

        FileOutputStream outStream = new FileOutputStream(compressedFile, false);
        for (byte b : compressed) {
            outStream.write(b);
        }
        outStream.close();

        outStream = new FileOutputStream(dictionaryFile, false);
        for (Element e : dictionary) {
            outStream.write(e.getValue());
            for (char bit : e.getKey().toCharArray()) {
                outStream.write((byte)bit);
            }
            outStream.write(0);
        }
        outStream.close();


//        System.out.println(Arrays.toString(Files.readAllBytes(Paths.get(compressedFile))));

//        System.out.println(Arrays.toString(Files.readAllBytes(Paths.get(dictionaryFile))));

    }

    public void decode(String decompressedFile, String compressedFile, String dictionaryFile) throws IOException {
        rootNodes.clear();
        dictionary.clear();
        compressed.clear();

        byte[] compressedBytes = Files.readAllBytes(Paths.get(compressedFile));
        byte[] dictionaryBytes = Files.readAllBytes(Paths.get(dictionaryFile));

        for (int i = 0; i < dictionaryBytes.length - 1; i++) {
            String key = "";
            int j = i + 1;
            for (; dictionaryBytes[j] != 0; j++) {
                key += (char) dictionaryBytes[j];
            }
            dictionary.add(new Element(dictionaryBytes[i], key));
            i += j - i;

        }

//        for (Element e : dictionary) {
//            System.out.println("Value: " +(char) e.getValue() + "  Key: " + e.getKey());
//        }



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

    private void compress() {
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
    }
}
