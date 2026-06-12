package jss.bettermapscale.mixin;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.BetterMapData;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MapState.class)
public abstract class MapStateNbtMixin {

    @ModifyVariable(
            method = "fromNbt",
            at = @At(
                    value = "STORE"
            ),
            ordinal = 0
    )
    private static MapState bettermapscale$loadDynamicColors(MapState mapState, NbtCompound nbt) {
        if (!nbt.getBoolean("bettermapscale_dynamic")) {
            return mapState;
        }

        int size = nbt.getInt("bettermapscale_size");
        byte[] colors = nbt.getByteArray("bettermapscale_colors");

        if (size <= 128 || colors.length != size * size) {
            Bettermapscale.LOGGER.warn(
                    "Invalid dynamic map NBT | Size: {} | Colors: {}",
                    size,
                    colors.length
            );
            return mapState;
        }

        MapStateAccessor accessor = (MapStateAccessor) mapState;
        BetterMapData data = (BetterMapData) mapState;

        accessor.bettermapscale$setColors(colors);

        Bettermapscale.LOGGER.info(
                "Dynamic map loaded from NBT | Size: {}x{} | Colors: {} | Dynamic: {}",
                size,
                size,
                colors.length,
                data.bettermapscale$isDynamic()
        );

        return mapState;
    }
}