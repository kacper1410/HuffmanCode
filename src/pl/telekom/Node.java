package pl.telekom;

public class Node implements Comparable<Node>{
    private Node leftNode;
    private Node rightNode;
    private int probability;
    private byte value;

    public void setLeftNode(Node leftNode) {
        this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
        this.rightNode = rightNode;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public int getProbability() {
        return probability;
    }

    public Node(byte value) {
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
        this.probability = 1;
    }

    public byte getValue() {
        return value;
    }

    public void incrementProbability() {
        this.probability++;
    }


    public Node(int probability, byte value) {
        this.probability = probability;
        this.value = value;
        this.leftNode = null;
        this.rightNode = null;
    }

    @Override
    public int compareTo(Node o) {
        return Integer.compare(this.probability, o.getProbability());
    }
}
