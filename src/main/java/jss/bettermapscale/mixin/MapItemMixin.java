package jss.bettermapscale.mixin;

import jss.bettermapscale.map.BetterMapData;
import jss.bettermapscale.map.DynamicMapColorUpdater;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.map.MapState;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FilledMapItem.class)
public abstract class MapItemMixin {

    @Inject(method = "updateColors", at = @At("HEAD"), cancellable = true)
    private void bettermapscale$updateDynamicColors(
            World world, Entity entity, MapState state, CallbackInfo ci
    ) {
        BetterMapData data = (BetterMapData) state;

        if (!data.bettermapscale$isDynamic()) {
            return;
        }

        DynamicMapColorUpdater.update(world, (PlayerEntity) entity, state);
        ci.cancel();
    }
}