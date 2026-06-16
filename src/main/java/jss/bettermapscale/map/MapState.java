package jss.bettermapscale.map;

public class MapState {

    private final int id;
    private final int size;

    private final int centerX;
    private final int centerZ;

    private final String dimension;

    private final byte[] colors;

    private final long createdAt;

    private final int scale;

    private boolean dirty;

    public MapState(
            int id,
            int size,
            int centerX,
            int centerZ,
            String dimension,
            long createdAt,
            int scale
    ) {

        validateSize(size);

        this.id = id;
        this.size = size;

        this.centerX = centerX;
        this.centerZ = centerZ;

        this.dimension = dimension;

        this.createdAt = createdAt;

        this.scale = scale;

        this.colors = new byte[size * size];

        this.dirty = true;
    }

    public MapState(
            int id,
            int size,
            int centerX,
            int centerZ,
            String dimension,
            long createdAt,
            int scale,
            byte[] colors
    ) {

        validateSize(size);

        int expectedLength = size * size;

        if (colors == null) {
            throw new IllegalArgumentException(
                    "Colors array cannot be null"
            );
        }

        if (colors.length != expectedLength) {
            throw new IllegalArgumentException(
                    "Invalid colors length. Expected "
                            + expectedLength
                            + " but got "
                            + colors.length
            );
        }

        this.id = id;
        this.size = size;

        this.centerX = centerX;
        this.centerZ = centerZ;

        this.dimension = dimension;

        this.createdAt = createdAt;

        this.scale = scale;

        this.colors = colors;

        this.dirty = false;
    }

    private static void validateSize(
            int size
    ) {

        for (int supportedSize :
                MapConstants.SUPPORTED_SIZES) {

            if (supportedSize == size) {
                return;
            }
        }

        throw new IllegalArgumentException(
                "Unsupported map size: "
                        + size
        );
    }



    private int getIndex(
            int x,
            int z
    ) {

        if (
                x < 0 ||
                        x >= size ||
                        z < 0 ||
                        z >= size
        ) {
            throw new IndexOutOfBoundsException(
                    "Position out of bounds: "
                            + x
                            + ", "
                            + z
            );
        }

        return z * size + x;
    }

    public byte getColor(
            int x,
            int z
    ) {
        return colors[getIndex(x, z)];
    }

    public void setColor(
            int x,
            int z,
            byte color
    ) {

        int index =
                getIndex(x, z);

        if (colors[index] == color) {
            return;
        }

        colors[index] = color;

        markDirty();
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

    public int getCenterX() {
        return centerX;
    }

    public int getCenterZ() {
        return centerZ;
    }

    public String getDimension() {
        return dimension;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public int getScale() {
        return scale;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void markDirty() {
        dirty = true;
    }

    public void clearDirty() {
        dirty = false;
    }
}