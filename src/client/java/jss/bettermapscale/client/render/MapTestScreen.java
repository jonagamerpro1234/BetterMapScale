package jss.bettermapscale.client.render;

import jss.bettermapscale.map.MapState;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

import java.util.Objects;

public class MapTestScreen extends Screen {

    private MapTexture texture;

    public MapTestScreen() {
        super(Text.of("Better Map Test"));
    }

    @Override
    protected void init() {
        if(texture == null) {
            texture = new MapTexture(256);

            MapState state =
                    new MapState(
                            0,
                            256,
                            0,
                            0,
                            "minecraft:overworld",
                            0,
                            1
                    );

            for(int z = 0; z < 256; z++) {
                for(int x = 0; x < 256; x++) {

                    state.setColor(
                            x,
                            z,
                            (byte)((x + z) % 128)
                    );
                }
            }

            texture = MapRenderer.getTexture(state);

           // texture.getTexture().upload();
/*
            for(int z = 0; z < 256; z++) {
                for(int x = 0; x < 256; x++) {
                    //int value = ((x/16) + (z/16)) % 2 == 0 ? 256 : 0;
                    //int rgb = 0xFF000000 | (value << 16) | (value << 8) | value;
                    int rgb = ((x/16) + (z/16)) % 2 == 0
                            ? 0xFFFFFFFF
                            : 0xFF000000;
                    texture.getImage().setColor(x,z,rgb);
                }
            }
            texture.getTexture().upload();*/
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {

        renderBackground(context);

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

        super.render(context, mouseX, mouseY, delta);
    }

    @Override
    public void close() {
        if(texture != null) {
            texture.close();
        }
        super.close();
    }
}
