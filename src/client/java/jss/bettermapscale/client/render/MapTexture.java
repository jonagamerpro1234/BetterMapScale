package jss.bettermapscale.client.render;

import jss.bettermapscale.map.MapState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;

public class MapTexture {

    private final NativeImage image;
    private final NativeImageBackedTexture texture;
    private final Identifier textureId;


    public MapTexture(int size) {
        this.image = new NativeImage(NativeImage.Format.RGBA, size, size, false);
        this.texture = new NativeImageBackedTexture(image);
        this.textureId = MinecraftClient.getInstance().getTextureManager().registerDynamicTexture("bettermap_" + System.nanoTime(), texture);
    }

    public void update(@NotNull MapState state){
        int size = state.getSize();
        for(int z = 0; z < size; z++){
            for(int x = 0; x < size; x++){
                int value = Byte.toUnsignedInt(state.getColor(x, z));
                int rgb = 0xFF000000 | (value << 16) | (value << 8) | value;
                image.setColor(x, z, rgb);
            }
        }
        texture.upload();
    }

    public NativeImage getImage() {
        return image;
    }

    public NativeImageBackedTexture getTexture() {
        return texture;
    }

    public Identifier getTextureId() {
        return textureId;
    }

    public void close(){
        MinecraftClient.getInstance().getTextureManager().destroyTexture(textureId);
        texture.close();
        image.close();
    }
}
