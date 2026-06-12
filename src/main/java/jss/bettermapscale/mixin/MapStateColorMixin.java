package jss.bettermapscale.mixin;

import jss.bettermapscale.map.BetterMapData;
import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(MapState.class)
public abstract class MapStateColorMixin {

    @Shadow
    public byte[] colors;

    @Shadow
    protected abstract void markDirty(int x, int z);

    /**
     * @author SrJss
     * @reason Usar ancho dinámico en vez de 128.
     */
    @Overwrite
    public boolean putColor(int x, int z, byte color) {
        BetterMapData data = (BetterMapData) this;
        int size = data.bettermapscale$getMapSize();

        int index = x + z * size;

        if (index < 0 || index >= this.colors.length) {
            return false;
        }

        byte oldColor = this.colors[index];

        if (oldColor != color) {
            this.setColor(x, z, color);
            return true;
        }

        return false;
    }

    /**
     * @author SrJss
     * @reason Usar ancho dinámico en vez de 128.
     */
    @Overwrite
    public void setColor(int x, int z, byte color) {
        BetterMapData data = (BetterMapData) this;
        int size = data.bettermapscale$getMapSize();

        int index = x + z * size;

        if (index < 0 || index >= this.colors.length) {
            return;
        }

        this.colors[index] = color;

        // Ojo: markDirty vanilla sigue pensado para 128.
        // Por ahora lo dejamos para compatibilidad.
        this.markDirty(
                Math.min(x, 127),
                Math.min(z, 127)
        );
    }
}