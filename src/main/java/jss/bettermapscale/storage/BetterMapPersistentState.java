package jss.bettermapscale.storage;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class BetterMapPersistentState extends PersistentState {

    private int nextId = 1;

    @Contract(value = " -> new", pure = true)
    public static @NotNull BetterMapPersistentState create() {

        System.out.println("[BetterMapScale] create()");

        return new BetterMapPersistentState();
    }

    public static @NotNull BetterMapPersistentState fromNbt(@NotNull NbtCompound nbt) {

        System.out.println("[BetterMapScale] fromNbt()");

        BetterMapPersistentState state =
                new BetterMapPersistentState();

        state.nextId = nbt.getInt("nextId");

        if (state.nextId <= 0) {
            state.nextId = 1;
        }

        return state;
    }

    public int createMapId() {

        System.out.println("[BetterMapScale] createMapId() called");

        markDirty();

        return nextId++;
    }

    @Override
    public NbtCompound writeNbt(@NotNull NbtCompound nbt) {

        System.out.println("[BetterMapScale] writeNbt()");

        nbt.putInt("nextId", nextId);

        return nbt;
    }
}