package jss.bettermapscale.client;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.client.render.MapTestScreen;
import jss.bettermapscale.item.BetterFilledMapItem;
import jss.bettermapscale.map.MapState;
import jss.bettermapscale.storage.MapManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import org.lwjgl.glfw.GLFW;

public class BettermapscaleClient implements ClientModInitializer {

    private static KeyBinding OPEN_TEST_SCREEN;

    @Override
    public void onInitializeClient() {
        Bettermapscale.LOGGER.info("BetterMapScale client initialized");

        OPEN_TEST_SCREEN = KeyBindingHelper.registerKeyBinding( new KeyBinding("key.bettermapscale.test", InputUtil.Type.KEYSYM
        , GLFW.GLFW_KEY_O, "category.bettermapscale"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {

            while (OPEN_TEST_SCREEN.wasPressed()) {

                if (client.player == null) {
                    continue;
                }

                if (client.player.getMainHandStack().isEmpty()) {
                    continue;
                }

                ItemStack stack =
                        client.player.getMainHandStack();

                if (!(stack.getItem() instanceof BetterFilledMapItem)) {
                    continue;
                }

                int mapId =
                        BetterFilledMapItem.getMapId(
                                stack
                        );

                if (mapId == -1) {
                    continue;
                }

                IntegratedServer server =
                        client.getServer();

                if (server == null) {
                    continue;
                }

                ServerWorld world =
                        server.getOverworld();

                if (world == null) {
                    continue;
                }

                MapState state =
                        MapManager.getMap(
                                world,
                                world.getRegistryKey()
                                        .getValue()
                                        .toString(),
                                mapId
                        );

                if (state == null) {
                    continue;
                }

                client.setScreen(
                        new MapTestScreen(
                                state
                        )
                );
            }
        });

    }
}