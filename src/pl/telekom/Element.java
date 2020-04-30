package pl.telekom;

public class Element {
    private byte value;
    private String key;

    public byte getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public Element(byte value, String key) {
        this.value = value;
        this.key = key;
    }
}
