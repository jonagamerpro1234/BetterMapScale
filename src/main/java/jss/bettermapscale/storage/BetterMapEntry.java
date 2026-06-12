package jss.bettermapscale.storage;

public class BetterMapEntry {

    private final int id;
    private final int size;

    public BetterMapEntry(int id, int size) {
        this.id = id;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }
}