package jss.bettermapscale.client.network;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.map.BetterMapData;
import jss.bettermapscale.mixin.MapStateAccessor;
import jss.bettermapscale.network.ModNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.map.MapState;
import org.jetbrains.annotations.NotNull;

public final class DynamicMapSyncReceiver {

    private DynamicMapSyncReceiver() {
    }

    public static void register() {
        ClientPlayNetworking.registerGlobalReceiver(ModNetworking.SYNC_DYNAMIC_MAP,
                (client, handler, buf, responseSender) -> {
                    int mapId = buf.readInt();
                    int size = buf.readInt();
                    byte[] colors = buf.readByteArray();

                    client.execute(() -> apply(client, mapId, size, colors));
                });
    }

    private static void apply(@NotNull MinecraftClient client, int mapId, int size, byte[] colors) {
        if (client.world == null) {
            return;
        }

        MapState state = client.world.getMapState("map_" + mapId);

        if (state == null) {
            Bettermapscale.LOGGER.warn("Client map state not found | id: {}", mapId);
            return;
        }

        BetterMapData data = (BetterMapData) state;
        MapStateAccessor accessor = (MapStateAccessor) state;

        accessor.bettermapscale$setColors(colors);
        //client.gameRenderer.getMapRenderer().clearStateTextures();
        Bettermapscale.LOGGER.info(
                "Dynamic map synced to client | id: {} | size: {}x{} | colors: {} | dynamic: {}",
                mapId,
                size,
                size,
                colors.length,
                data.bettermapscale$isDynamic()
        );
    }
}