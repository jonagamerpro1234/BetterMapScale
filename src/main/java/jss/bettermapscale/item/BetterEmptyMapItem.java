package jss.bettermapscale.item;

import jss.bettermapscale.map.MapConstants;
import jss.bettermapscale.map.MapState;
import jss.bettermapscale.storage.MapManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.jetbrains.annotations.NotNull;

public class BetterEmptyMapItem extends Item {

    public BetterEmptyMapItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(@NotNull World world, @NotNull PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (!world.isClient) {
            MapState state = MapManager.createMap((ServerWorld) world, MapConstants.DEFAULT_SIZE, user.getBlockX(), user.getBlockZ(), world.getRegistryKey().getValue().toString());

            ItemStack filledMap = new ItemStack(ModItems.BETTER_FILLED_MAP);
            BetterFilledMapItem.setMapData(filledMap, state.getId(), state.getSize(), state.getDimension());

            if (!user.getAbilities().creativeMode) {
                stack.decrement(1);
            }

            user.giveItemStack(filledMap);
        }

        return TypedActionResult.success(stack, world.isClient());
    }
}