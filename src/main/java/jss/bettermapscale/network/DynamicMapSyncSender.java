package jss.bettermapscale.network;

import jss.bettermapscale.map.BetterMapData;
import jss.bettermapscale.mixin.MapStateAccessor;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.map.MapState;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import io.netty.buffer.Unpooled;

public final class DynamicMapSyncSender {

    private DynamicMapSyncSender() {
    }

    public static void send(PlayerEntity player, MapState state) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }

        BetterMapData data = (BetterMapData) state;
        MapStateAccessor accessor = (MapStateAccessor) state;

        if (!data.bettermapscale$isDynamic()) {
            return;
        }

        ItemStack stack = player.getStackInHand(Hand.MAIN_HAND);

        if (!(stack.getItem() instanceof FilledMapItem)) {
            return;
        }

        Integer mapId = FilledMapItem.getMapId(stack);

        if (mapId == null) {
            return;
        }

        byte[] colors = accessor.bettermapscale$getColors();

        PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());

        buf.writeInt(mapId);
        buf.writeInt(data.bettermapscale$getMapSize());
        buf.writeByteArray(colors);

        ServerPlayNetworking.send(serverPlayer, ModNetworking.SYNC_DYNAMIC_MAP, buf);
    }
}