package jss.bettermapscale.storage;

import jss.bettermapscale.map.BetterMapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.WorldSavePath;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

public final class BetterMapStorage {

    private static final String FOLDER_NAME = "bettermapscale";
    private static final String MAPS_FOLDER = "maps";
    private static final int FILE_VERSION = 1;

    private BetterMapStorage() {
    }

    @SuppressWarnings("all")
    public static void saveMap(ServerWorld world, @NotNull BetterMapState map) {

        try {

            File mapsFolder = getMapsFolder(world, map.getDimension());


            if (!mapsFolder.exists()) {
                mapsFolder.mkdirs();
            }

            File mapFile = new File(mapsFolder, "map_" + map.getId() + ".dat");

            NbtCompound nbt = getNbtCompound(map);

            nbt.putByteArray(
                    "colors",
                    map.getColors()
            );

            NbtIo.writeCompressed(
                    nbt,
                    mapFile
            );

            map.clearDirty();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull NbtCompound getNbtCompound(@NotNull BetterMapState map) {
        NbtCompound nbt = new NbtCompound();

        nbt.putInt(
                "version",
                FILE_VERSION
        );

        nbt.putInt(
                "id",
                map.getId()
        );

        nbt.putInt(
                "size",
                map.getSize()
        );

        nbt.putInt(
                "centerX",
                map.getCenterX()
        );

        nbt.putInt(
                "centerZ",
                map.getCenterZ()
        );

        nbt.putString(
                "dimension",
                map.getDimension()
        );

        nbt.putLong(
                "createdAt",
                map.getCreatedAt()
        );

        nbt.putInt(
                "scale",
                map.getScale()
        );
        return nbt;
    }

    public static @Nullable BetterMapState loadMap(ServerWorld world, String dimension, int mapId) {
        try {

            File mapFile = new File(getMapsFolder(world, dimension), "map_" + mapId + ".dat");

            if (!mapFile.exists()) {
                return null;
            }

            NbtCompound nbt = NbtIo.readCompressed(mapFile);
            int version = nbt.getInt("version");

            if (version != FILE_VERSION) {
                throw new IOException("Unsupported map version: " + version);
            }

            int id = nbt.getInt("id");
            int size = nbt.getInt("size");
            int centerX = nbt.getInt("centerX");
            int centerZ = nbt.getInt("centerZ");
            dimension = nbt.getString("dimension");

            long createdAt = nbt.getLong("createdAt");

            int scale = nbt.getInt("scale");

            byte[] colors = nbt.contains("colors") ? nbt.getByteArray("colors") : new byte[size * size];

            return new BetterMapState(
                    id,
                    size,
                    centerX,
                    centerZ,
                    dimension,
                    createdAt,
                    scale,
                    colors
            );

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static @NotNull File getMapsFolder(
            @NotNull ServerWorld world,
            String dimension
    ) {

        File worldFolder =
                world.getServer()
                        .getSavePath(
                                WorldSavePath.ROOT
                        )
                        .toFile();

        File mapsFolder =
                new File(
                        new File(
                                worldFolder,
                                "data/" + FOLDER_NAME
                        ),
                        MAPS_FOLDER
                );

        return new File(
                mapsFolder,
                getDimensionFolder(
                        dimension
                )
        );
    }

    @Contract(pure = true)
    private static @NotNull String getDimensionFolder(
            @NotNull String dimension
    ) {
        return dimension.replace(
                ':',
                '_'
        );
    }
}