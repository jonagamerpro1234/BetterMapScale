package jss.bettermapscale.mixin;

import jss.bettermapscale.map.BetterMapData;
import net.minecraft.item.map.MapState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.item.map.MapState$UpdateData")
public abstract class MapStateUpdateDataMixin {

    @Inject(method = "setColorsTo", at = @At("HEAD"), cancellable = true)
    private void bettermapscale$cancelVanillaColorSync(MapState mapState, CallbackInfo ci) {
        BetterMapData data = (BetterMapData) mapState;

        if (data.bettermapscale$isDynamic()) {
            ci.cancel();
        }
    }
}