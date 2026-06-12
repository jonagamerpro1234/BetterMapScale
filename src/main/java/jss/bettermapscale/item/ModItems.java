package jss.bettermapscale.item;

import jss.bettermapscale.Bettermapscale;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class ModItems {

    public static final Item BETTER_EMPTY_MAP = register(
            "better_empty_map",
            new BetterEmptyMapItem(new Item.Settings().maxCount(64))
    );

    public static final Item BETTER_FILLED_MAP = register(
            "better_filled_map",
            new BetterFilledMapItem(new Item.Settings().maxCount(1))
    );

    private static Item register(String name, Item item) {
        return Registry.register(
                Registries.ITEM,
                Identifier.of(Bettermapscale.MOD_ID, name),
                item
        );
    }

    public static void initialize() {
        Bettermapscale.LOGGER.info("Registering BetterMapScale items");
    }

    private ModItems() {
    }
}