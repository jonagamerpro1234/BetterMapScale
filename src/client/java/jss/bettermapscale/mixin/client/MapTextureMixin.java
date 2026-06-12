package jss.bettermapscale.mixin.client;

import jss.bettermapscale.map.BetterMapData;
import net.minecraft.block.MapColor;
import net.minecraft.client.render.MapRenderer;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.item.map.MapState;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.render.MapRenderer$MapTexture")
public abstract class MapTextureMixin {

    @Shadow
    private MapState state;

    @Shadow
    @Final
    private NativeImageBackedTexture texture;

    @Redirect(
            method = "<init>",
            at = @At(
                    value = "NEW",
                    target = "net/minecraft/client/texture/NativeImageBackedTexture"
            )
    )
    private @NotNull NativeImageBackedTexture bettermapscale$createDynamicTexture(
            int width,
            int height,
            boolean useStb
    ) {
        int size = bettermapscale$getTextureSize();
        return new NativeImageBackedTexture(size, size, useStb);
    }

    /**
     * @author SrJss
     * @reason Permite renderizar mapas con tamaño dinámico en vez de 128x128.
     */
    @Overwrite
    private void updateTexture() {
        int size = bettermapscale$getTextureSize();

        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                int index = x + y * size;

                if (index >= this.state.colors.length) {
                    continue;
                }

                this.texture.getImage().setColor(
                        x,
                        y,
                        MapColor.getRenderColor(this.state.colors[index])
                );
            }
        }

        this.texture.upload();
    }

    private int bettermapscale$getTextureSize() {
        BetterMapData data = (BetterMapData) this.state;

        if (data.bettermapscale$isDynamic()) {
            return data.bettermapscale$getMapSize();
        }

        return 128;
    }
}