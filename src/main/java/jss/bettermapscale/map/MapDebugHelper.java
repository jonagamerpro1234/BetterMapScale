package jss.bettermapscale.map;

public final class MapDebugHelper {

    private MapDebugHelper() {
    }

    public static int countFilledPixels(byte[] colors) {
        int count = 0;

        for (byte color : colors) {
            if (color != 0) {
                count++;
            }
        }

        return count;
    }

    public static double getFilledPercent(byte[] colors) {
        if (colors.length == 0) {
            return 0.0;
        }

        return (countFilledPixels(colors) * 100.0) / colors.length;
    }
}