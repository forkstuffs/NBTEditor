package io.github.bananapuncher714.nbteditor.tag;

import org.jetbrains.annotations.NotNull;

public abstract class NBTList<T extends NBTBase> extends NBTBase {

    protected NBTList(@NotNull final Object nbtlist) {
        super(nbtlist);
    }

    @NotNull
    public abstract T setGeneric(int index, @NotNull T nbt);

    public abstract void addGeneric(int index, @NotNull T nbt);

    @NotNull
    public abstract T remove(int index);

    public abstract boolean set(int index, @NotNull NBTBase nbt);

    public abstract boolean add(int index, @NotNull NBTBase nbt);

    public abstract byte type();

}
