package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTList;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public final class NBTByteArray extends NBTList<NBTByte> {

    public NBTByteArray(@NotNull final Object nbtBase) {
        super(nbtBase);
    }

    @NotNull
    @Override
    public NBTByte get(final int key) {
        return new NBTByte(this.invokeWithoutDefault("get", key));
    }

    @NotNull
    @Override
    public NBTByte set(final int key, @NotNull final NBTByte value) {
        return new NBTByte(this.invokeWithoutDefault("set", key, value.nbt()));
    }

    @Override
    public void add(final int key, @NotNull final NBTByte value) {
        this.invokeWithoutDefault("add", key, value.nbt());
    }

    @NotNull
    @Override
    public NBTByte remove(final int key) {
        return new NBTByte(this.invokeWithoutDefault("remove", key));
    }

    @Override
    public boolean setWithType(final int key, @NotNull final NBTBase value) {
        return this.invoke("setWithType", false, key, value.nbt());
    }

    @Override
    public boolean completeSetWithType(final int key, @NotNull final NBTBase value) {
        return this.invoke("completeSetWithType", false, key, value.nbt());
    }

    @Override
    public void clear() {
        this.invokeWithoutDefault("clear");
    }

    @Override
    public int size() {
        return this.invoke("size", 0);
    }

    @NotNull
    @Override
    public NBTType type() {
        return NBTType.BYTE_ARRAY;
    }

    @NotNull
    @Override
    public Function<Object, NBTBase> createNew() {
        return NBTByteArray::new;
    }

}
