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
    private final MapState state;

    public MapTestScreen(MapState state) {
        super(Text.of("Better Map Test"));
        this.state = state;
    }

    @Override
    protected void init() {

        texture = MapRenderer.getTexture(state);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        renderBackground(context);

        if (texture != null) {

            int x = (width - state.getSize()) / 2;
            int y = (height - state.getSize()) / 2;

            context.drawTexture(
                    texture.getTextureId(),
                    x,
                    y,
                    0,
                    0,
                    state.getSize(),
                    state.getSize(),
                    state.getSize(),
                    state.getSize()
            );
        }

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        super.close();
    }
}
