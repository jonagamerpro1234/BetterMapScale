package jss.bettermapscale.client.render;

import jss.bettermapscale.map.MapGenerator;
import jss.bettermapscale.map.MapState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;

public class MapTestScreen extends Screen {

    private MapTexture texture;

    public MapTestScreen() {
        super(Text.of("Better Map Test"));
    }

    @Override
    protected void init() {

        if (texture != null) {
            return;
        }

        MinecraftClient client = MinecraftClient.getInstance();

        if (client.world == null) {
            return;
        }

        IntegratedServer server = client.getServer();

        if (server == null) {
            return;
        }

        ServerWorld world = server.getOverworld();

        if (world == null) {
            return;
        }

        MapState state = new MapState(0,256,0,0,"minecraft:overworld",System.currentTimeMillis(),1);
        MapGenerator.generateMap(world,state);
        texture = MapRenderer.getTexture(state);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        renderBackground(context);

        if (texture != null) {
            context.drawTexture(
                    texture.getTextureId(),
                    100,
                    100,
                    0,
                    0,
                    256,
                    256,
                    256,
                    256
            );
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        super.close();
    }
}
