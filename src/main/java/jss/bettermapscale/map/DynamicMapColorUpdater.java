package jss.bettermapscale.map;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.mixin.MapStateAccessor;
import jss.bettermapscale.network.DynamicMapSyncSender;
import net.minecraft.block.BlockState;
import net.minecraft.block.MapColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.map.MapState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.World;

public final class DynamicMapColorUpdater {

    private static final int MAP_BRIGHTNESS_NORMAL = 2;

    private DynamicMapColorUpdater() {
    }

    public static void update(World world, PlayerEntity player, MapState state) {
        BetterMapData data = (BetterMapData) state;
        MapStateAccessor accessor = (MapStateAccessor) state;

        int size = data.bettermapscale$getMapSize();
        byte[] colors = accessor.bettermapscale$getColors();

        if (colors.length != size * size) {
            Bettermapscale.LOGGER.warn(
                    "Invalid dynamic map color array | Colors: {} | Expected: {}",
                    colors.length,
                    size * size
            );
            return;
        }

        int centerX = accessor.bettermapscale$getCenterX();
        int centerZ = accessor.bettermapscale$getCenterZ();

        int startX = centerX - size / 2;
        int startZ = centerZ - size / 2;

        BlockPos.Mutable pos = new BlockPos.Mutable();

        for (int mapZ = 0; mapZ < size; mapZ++) {
            for (int mapX = 0; mapX < size; mapX++) {
                int worldX = startX + mapX;
                int worldZ = startZ + mapZ;

                int topY = world.getTopY(Heightmap.Type.WORLD_SURFACE, worldX, worldZ) - 1;

                if (topY < world.getBottomY()) {
                    colors[DynamicMapHelper.getIndex(mapX, mapZ, size)] = 0;
                    continue;
                }

                pos.set(worldX, topY, worldZ);

                BlockState blockState = world.getBlockState(pos);
                MapColor mapColor = blockState.getMapColor(world, pos);

                byte color = getMapColorByte(mapColor);
                colors[DynamicMapHelper.getIndex(mapX, mapZ, size)] = color;
            }
        }

        state.markDirty();
        DynamicMapSyncSender.send(player, state);

        Bettermapscale.LOGGER.info(
                "Dynamic terrain map updated | Size: {}x{} | Colors filled: {}",
                size,
                size,
                colors.length
        );
    }

    private static byte getMapColorByte(MapColor mapColor) {
        if (mapColor == MapColor.CLEAR) {
            return 0;
        }

        return (byte) (mapColor.id * 4 + MAP_BRIGHTNESS_NORMAL);
    }
}