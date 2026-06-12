package jss.bettermapscale.map;

public final class MapScaleHelper {

    public static final int VANILLA_MAP_SIZE = 128;

    /*
     * Por seguridad inicial usamos 512.
     * Después lo subimos a 1024 o 2048.
     */
    public static final int MAX_DYNAMIC_SIZE = 512;

    private MapScaleHelper() {
    }

    public static int getRealMapSize(byte scale) {
        int size = VANILLA_MAP_SIZE * (1 << scale);
        return Math.min(size, MAX_DYNAMIC_SIZE);
    }

    public static int getRealMapArea(byte scale) {
        int size = getRealMapSize(scale);
        return size * size;
    }

    public static boolean isValidScale(byte scale) {
        return scale >= 0 && scale <= 4;
    }

    public static boolean shouldUseDynamicMap(byte scale) {
        return isValidScale(scale) && getRealMapSize(scale) > VANILLA_MAP_SIZE;
    }
}