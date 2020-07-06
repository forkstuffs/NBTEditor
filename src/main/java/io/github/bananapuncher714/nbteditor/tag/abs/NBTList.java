package io.github.bananapuncher714.nbteditor.tag.abs;

import java.util.AbstractList;
import org.jetbrains.annotations.NotNull;

public abstract class NBTList<T extends NBTBase> extends AbstractList<T> implements NBTBase {

    @Override
    @NotNull
    public abstract T get(int key);

    @Override
    @NotNull
    public abstract T set(int key, @NotNull T value);

    @Override
    public abstract void add(int key, @NotNull T value);

    @Override
    @NotNull
    public abstract T remove(int key);

    public abstract boolean setWithType(int key, @NotNull NBTBase value);

    public abstract boolean completeSetWithType(int key, @NotNull NBTBase value);

}
