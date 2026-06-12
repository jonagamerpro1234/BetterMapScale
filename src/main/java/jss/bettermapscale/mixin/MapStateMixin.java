package jss.bettermapscale.mixin;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.BetterMapData;
import jss.bettermapscale.map.MapScaleHelper;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MapState.class)
public abstract class MapStateMixin implements BetterMapData {

    @Final
    @Shadow
    public byte scale;

    @Mutable
    @Shadow
    public byte[] colors;

    @Unique
    private int bettermapscale$mapSize = MapScaleHelper.VANILLA_MAP_SIZE;

    @Unique
    private boolean bettermapscale$dynamic = false;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void bettermapscale$initDynamicMap(
            int centerX,
            int centerZ,
            byte scale,
            boolean showIcons,
            boolean unlimitedTracking,
            boolean locked,
            RegistryKey<World> dimension,
            CallbackInfo ci
    ) {
        int size = MapScaleHelper.getRealMapSize(scale);

        this.bettermapscale$mapSize = size;
        this.bettermapscale$dynamic = size > MapScaleHelper.VANILLA_MAP_SIZE;

        if (this.bettermapscale$dynamic && this.colors.length != size * size) {
            this.colors = new byte[size * size];

            Bettermapscale.LOGGER.info(
                    "Dynamic map initialized | Level: {} | Size: {}x{} | Colors: {}",
                    scale,
                    size,
                    size,
                    this.colors.length
            );
        }
    }

   // @Inject(method = "writeNbt", at = @At("TAIL"))
    private void bettermapscale$writeDynamicNbt(
            @NotNull NbtCompound nbt,
            CallbackInfoReturnable<NbtCompound> cir
    ) {
        nbt.putBoolean("bettermapscale_dynamic", this.bettermapscale$dynamic);
        nbt.putInt("bettermapscale_size", this.bettermapscale$mapSize);
        nbt.putByteArray("bettermapscale_colors", this.colors);
    }

    @Override
    public int bettermapscale$getMapSize() {
        return this.bettermapscale$mapSize;
    }

    @Override
    public int bettermapscale$getMapArea() {
        return this.bettermapscale$mapSize * this.bettermapscale$mapSize;
    }

    @Override
    public boolean bettermapscale$isDynamic() {
        return this.bettermapscale$dynamic;
    }
}