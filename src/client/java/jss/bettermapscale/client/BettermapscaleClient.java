package jss.bettermapscale.client;

import jss.bettermapscale.Bettermapscale;
import jss.bettermapscale.client.render.MapTestScreen;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.input.Input;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
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
               client.setScreen(new MapTestScreen());
           }
        });

    }
}