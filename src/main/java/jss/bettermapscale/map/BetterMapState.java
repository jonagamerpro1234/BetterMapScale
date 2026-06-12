package jss.bettermapscale.map;

public class BetterMapState {

    private final int id;
    private final int size;
    private final byte[] colors;

    public BetterMapState(int id, int size) {
        this.id = id;
        this.size = size;
        this.colors = new byte[size * size];
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public byte[] getColors() {
        return colors;
    }

    public int getArea() {
        return size * size;
    }
}