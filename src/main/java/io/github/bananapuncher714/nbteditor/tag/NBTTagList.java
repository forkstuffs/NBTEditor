package io.github.bananapuncher714.nbteditor.tag;

import org.jetbrains.annotations.NotNull;

public final class NBTTagList extends NBTList<NBTBase> {

    public NBTTagList(@NotNull final Object nbtlist) {
        super(nbtlist);
    }

    @Override
    public NBTBase setGeneric(final int index, @NotNull final NBTBase nbt) {
        return null;
    }

    @Override
    public void addGeneric(final int index, @NotNull final NBTBase nbt) {

    }

    @Override
    public NBTBase remove(final int index) {
        return null;
    }

    @Override
    public boolean set(final int index, @NotNull final NBTBase nbt) {
        return false;
    }

    @Override
    public boolean add(final int index, @NotNull final NBTBase nbt) {
        return false;
    }

    @Override
    public byte type() {
        return 0;
    }

}