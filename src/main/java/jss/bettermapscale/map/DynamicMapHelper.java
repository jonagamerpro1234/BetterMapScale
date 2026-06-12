package jss.bettermapscale.map;

public final class DynamicMapHelper {

    private DynamicMapHelper() {
    }

    public static int getIndex(int x, int z, int size) {
        return x + z * size;
    }

    public static boolean isInside(int x, int z, int size) {
        return x >= 0 && z >= 0 && x < size && z < size;
    }

    public static int clampToMap(int value, int size) {
        if (value < 0) {
            return 0;
        }

        if (value >= size) {
            return size - 1;
        }

        return value;
    }
}