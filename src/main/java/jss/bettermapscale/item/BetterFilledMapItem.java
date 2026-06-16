package jss.bettermapscale.item;

import jss.bettermapscale.map.MapConstants;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class BetterFilledMapItem extends Item {

    public BetterFilledMapItem(Settings settings) {
        super(settings);
    }

    public static int getMapId(@NotNull ItemStack stack) {
        if (stack.getNbt() == null || !stack.getNbt().contains(MapConstants.MAP_ID)) {
            return -1;
        }

        return stack.getNbt().getInt(MapConstants.MAP_ID);
    }

    public static int getMapSize(@NotNull ItemStack stack) {
        if (stack.getNbt() == null || !stack.getNbt().contains(MapConstants.MAP_ID)) {
            return -1;
        }
        return stack.getNbt().getInt(MapConstants.MAP_SIZE);
    }

    public static boolean hasMapData(@NotNull ItemStack stack) {
        return getMapId(stack) != -1;
    }
}