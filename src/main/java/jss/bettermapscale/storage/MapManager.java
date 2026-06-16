package jss.bettermapscale.storage;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.MapGenerator;
import jss.bettermapscale.map.MapState;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class MapManager {

    private static final Map<Integer, MapState> MAPS = new HashMap<>();

    public static @NotNull MapState createMap(ServerWorld world, int size, int centerX, int centerZ, String dimension) {
        int id = createMapId(world);

        MapState state =
                new MapState(
                        id,
                        size,
                        centerX,
                        centerZ,
                        dimension,
                        System.currentTimeMillis(),
                        1
                );

        MAPS.put(id, state);

        MapGenerator.generateMap(world, state);
        MapStorage.saveMap(world,state);
        state.clearDirty();

        Bettermapscale.LOGGER.info(
                "Created BetterMap | id={} | size={} | center=({}, {}) | dimension={}",
                id,
                size,
                centerX,
                centerZ,
                dimension
        );

        return state;
    }

    public static MapState getMap(ServerWorld world, String dimension, int id) {

        MapState state =
                MAPS.get(id);

        if (state != null) {
            return state;
        }

        state = MapStorage.loadMap(
                world,
                dimension,
                id
        );

        if (state != null) {
            MAPS.put(id, state);
        }

        return state;
    }

    public static void unloadMap(int id) {
        MAPS.remove(id);
    }

    public static void clear() {
        MAPS.clear();
    }

    public static boolean exists(int id) {
        return MAPS.containsKey(id);
    }

    public static int createMapId(@NotNull ServerWorld world) {
        MapPersistentState state =
                world.getPersistentStateManager()
                        .getOrCreate(
                                MapPersistentState::fromNbt,
                                MapPersistentState::create,
                                "bettermapscale"
                        );

        return state.createMapId();
    }

    public static void saveMap(ServerWorld world, @NotNull MapState state) {
        if (!state.isDirty()) {
            return;
        }

        MapStorage.saveMap(
                world,
                state
        );

        state.clearDirty();
    }
}