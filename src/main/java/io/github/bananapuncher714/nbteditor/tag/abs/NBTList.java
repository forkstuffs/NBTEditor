package io.github.bananapuncher714.nbteditor.tag.abs;

import org.jetbrains.annotations.NotNull;

public abstract class NBTList<T extends NBTBase> extends NBTBase {

    protected NBTList(@NotNull final Object nbtBase) {
        super(nbtBase);
    }

    @NotNull
    public abstract T get(int key);

    @NotNull
    public abstract T set(int key, @NotNull T value);

    public abstract void add(int key, @NotNull T value);

    @NotNull
    public abstract T remove(int key);

    public abstract boolean setWithType(int key, @NotNull NBTBase value);

    public abstract boolean completeSetWithType(int key, @NotNull NBTBase value);

    public abstract void clear();

    public abstract int size();

}
