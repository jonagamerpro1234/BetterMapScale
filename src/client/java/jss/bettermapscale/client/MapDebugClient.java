package jss.bettermapscale.client;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.BetterMapData;
import jss.bettermapscale.map.MapDebugHelper;
import jss.bettermapscale.map.MapScaleHelper;
import jss.bettermapscale.mixin.legacy.MapStateAccessor;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;

public final class MapDebugClient {

    private static int tickCounter = 0;

    private MapDebugClient() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            tickCounter++;

            if (tickCounter < 40) {
                return;
            }

            tickCounter = 0;
            checkMap(client);
        });
    }

    private static void checkMap(MinecraftClient client) {
        if (client.player == null || client.world == null) {
            return;
        }

        ItemStack stack = client.player.getMainHandStack();

        if (!stack.isOf(Items.FILLED_MAP)) {
            return;
        }

        MapState mapState = FilledMapItem.getMapState(stack, client.world);

        if (mapState == null) {
            return;
        }

        MapStateAccessor accessor = (MapStateAccessor) mapState;
        BetterMapData betterMapData = (BetterMapData) mapState;

        byte scale = accessor.bettermapscale$getScale();
        byte[] colors = accessor.bettermapscale$getColors();

        int vanillaSize = MapScaleHelper.VANILLA_MAP_SIZE;
        int expectedSize = betterMapData.bettermapscale$getMapSize();
        int expectedArea = betterMapData.bettermapscale$getMapArea();

        int filledPixels = MapDebugHelper.countFilledPixels(colors);
        double filledPercent = MapDebugHelper.getFilledPercent(colors);

        Bettermapscale.LOGGER.info(
                "Map debug | Level: {} | Vanilla: {}x{} | Dynamic: {} | Size: {}x{} | Colors: {} / Expected: {} | Filled: {} ({}%)",
                scale,
                vanillaSize,
                vanillaSize,
                betterMapData.bettermapscale$isDynamic(),
                expectedSize,
                expectedSize,
                colors.length,
                expectedArea,
                filledPixels,
                String.format("%.2f", filledPercent)
        );
    }
}