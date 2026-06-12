package jss.bettermapscale.storage;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.BetterMapState;
import net.minecraft.server.world.ServerWorld;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public final class BetterMapManager {

    private static final Map<Integer, BetterMapState> MAPS = new HashMap<>();

    public static @NotNull BetterMapState createMap(
            ServerWorld world,
            int size
    ) {

        int id = createMapId(world);

        BetterMapState state =
                new BetterMapState(id, size);

        MAPS.put(id, state);

        Bettermapscale.LOGGER.info(
                "Created BetterMap | id={} | size={} | area={}",
                id,
                size,
                state.getArea()
        );

        return state;
    }

    public static BetterMapState getMap(int id) {
        return MAPS.get(id);
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