package jss.bettermapscale.storage;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.BetterMapGenerator;
import jss.bettermapscale.map.BetterMapState;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class BetterMapManager {

    private static final Map<Integer, BetterMapState> MAPS = new HashMap<>();

    public static @NotNull BetterMapState createMap(
            ServerWorld world,
            int size,
            int centerX,
            int centerZ,
            String dimension
    ) {

        int id = createMapId(world);

        BetterMapState state =
                new BetterMapState(
                        id,
                        size,
                        centerX,
                        centerZ,
                        dimension,
                        System.currentTimeMillis(),
                        1
                );

        MAPS.put(id, state);

        BetterMapGenerator.generateMap(world, state);
        BetterMapStorage.saveMap(world,state);

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

    public static BetterMapState getMap(ServerWorld world, String dimension, int id) {

        BetterMapState state =
                MAPS.get(id);

        if (state != null) {
            return state;
        }

        state = BetterMapStorage.loadMap(
                world,
                dimension,
                id
        );

        if (state != null) {
            MAPS.put(id, state);
        }

        return state;
    }

    public static boolean exists(int id) {
        return MAPS.containsKey(id);
    }

    public static int createMapId(@NotNull ServerWorld world) {

        BetterMapPersistentState state =
                world.getPersistentStateManager()
                        .getOrCreate(
                                BetterMapPersistentState::fromNbt,
                                BetterMapPersistentState::create,
                                "bettermapscale"
                        );

        return state.createMapId();
    }

    private BetterMapManager() {
    }
}