package jss.bettermapscale.mixin.client;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.BetterMapData;
import net.minecraft.client.render.MapRenderer;
import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MapRenderer.class)
public abstract class MapRendererMixin {

    @Inject(method = "getMapTexture", at = @At("HEAD"))
    private void bettermapscale$debugMapTexture(
            int id,
            MapState state,
            CallbackInfoReturnable<?> cir
    ) {
        BetterMapData data = (BetterMapData) state;

        if (!data.bettermapscale$isDynamic()) {
            return;
        }

        Bettermapscale.LOGGER.info(
                "Rendering dynamic map | id: {} | size: {}x{} | area: {}",
                id,
                data.bettermapscale$getMapSize(),
                data.bettermapscale$getMapSize(),
                data.bettermapscale$getMapArea()
        );
    }
}