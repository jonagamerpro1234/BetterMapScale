package jss.bettermapscale.storage;

public class MapEntry {

    private final int id;
    private final int size;

    public MapEntry(int id, int size) {
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