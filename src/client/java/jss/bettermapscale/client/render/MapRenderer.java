package jss.bettermapscale.client.render;

import jss.bettermapscale.map.MapState;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MapRenderer {

    private static final Map<Integer,MapTexture> TEXTURES = new HashMap<>();

    public static @NotNull MapTexture getTexture(@NotNull MapState state){
        MapTexture texture = TEXTURES.get(state.getId());
        if(texture == null){
            texture = new MapTexture(state.getSize());
            TEXTURES.put(state.getId(), texture);
            texture.update(state);
            state.clearDirty();
            return  texture;
        }

        if(state.isDirty()){
            texture.update(state);
            state.clearDirty();
        }

        return texture;
    }

    public static void removeTexture(int id){
        MapTexture texture = TEXTURES.remove(id);
        if(texture != null){
            texture.close();
        }
    }

    public static void clear(){
        for(MapTexture texture : TEXTURES.values()){
            texture.close();
        }

        TEXTURES.clear();
    }

}
