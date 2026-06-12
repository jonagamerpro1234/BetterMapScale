package jss.bettermapscale.mixin;

import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MapState.class)
public interface MapStateAccessor {

    @Accessor("scale")
    byte bettermapscale$getScale();

    @Accessor("centerX")
    int bettermapscale$getCenterX();

    @Accessor("centerZ")
    int bettermapscale$getCenterZ();

    @Accessor("colors")
    byte[] bettermapscale$getColors();

    @Accessor("colors")
    void bettermapscale$setColors(byte[] colors);
}