package jss.bettermapscale.map;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.Heightmap;
import org.jetbrains.annotations.NotNull;

public class MapGenerator {

    public static void generateMap(ServerWorld world, @NotNull MapState state) {
        int size = state.getSize();

        int startX = state.getCenterX() - (size / 2);
        int startZ = state.getCenterZ() - (size / 2);

        for(int z = 0; z < size; z++) {
            for(int x = 0; x < size; x++) {

                int worldX = startX + x;
                int worldZ = startZ + z;
                int height = world.getTopY(Heightmap.Type.WORLD_SURFACE, worldX, worldZ);

                byte color = MapColorResolver.getColorFromHeight(height);
                state.setColor(x,z,color);
            }
        }
    }

}
