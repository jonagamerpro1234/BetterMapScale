package jss.bettermapscale.client;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.client.render.MapTestScreen;
import jss.bettermapscale.map.MapGenerator;
import jss.bettermapscale.map.MapState;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import org.lwjgl.glfw.GLFW;

public class BettermapscaleClient implements ClientModInitializer {

    private static KeyBinding OPEN_TEXT_SCREEN;

    @Override
    public void onInitializeClient() {
        Bettermapscale.LOGGER.info("BetterMapScale client initialized");

        OPEN_TEXT_SCREEN = KeyBindingHelper.registerKeyBinding( new KeyBinding("key.bettermapscale.test", InputUtil.Type.KEYSYM
        , GLFW.GLFW_KEY_O, "category.bettermapscale"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
           while (OPEN_TEXT_SCREEN.wasPressed()) {
               if (client.player == null) {
                   continue;
               }

               IntegratedServer server = client.getServer();

               if (server == null) {
                   continue;
               }

               ServerWorld world = server.getOverworld();

               if (world == null) {
                   continue;
               }

               int centerX = (int) client.player.getX();
               int centerZ = (int) client.player.getZ();

               MapState state =
                       new MapState(
                               (int) System.currentTimeMillis(),
                               256,
                               centerX,
                               centerZ,
                               "minecraft:overworld",
                               System.currentTimeMillis(),
                               1
                       );

               MapGenerator.generateMap(
                       world,
                       state
               );

               client.setScreen(new MapTestScreen(state));
           }
        });

    }
}