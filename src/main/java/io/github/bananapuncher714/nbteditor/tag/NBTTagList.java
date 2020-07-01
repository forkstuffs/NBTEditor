package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTBaseEnvelope;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTList;
import org.jetbrains.annotations.NotNull;

public final class NBTTagList extends NBTBaseEnvelope implements NBTList {

    public NBTTagList(@NotNull final Object nbt) {
        super(nbt, NBTType.TAG_LIST);
    }

    @Override
    public void add(@NotNull final Object value) {

    }

    @Override
    public void add(final int key, @NotNull final Object value) {

    }

    @Override
    public void set(final int key, @NotNull final Object value) {

    }

    @Override
    public void remove(final int key) {

    }

    @NotNull
    @Override
    public NBTBase get(final int key) {
        return null;
    }

    @NotNull
    @Override
    public NBTCompound getCompound(final int key) {
        return null;
    }

    @NotNull
    @Override
    public NBTList getList(final int key) {
        return null;
    }

    @Override
    public short getShort(final int key) {
        return 0;
    }

    @Override
    public int getInt(final int key) {
        return 0;
    }

    @NotNull
    @Override
    public int[] getIntArray(final int key) {
        return new int[0];
    }

    @Override
    public double getDouble(final int key) {
        return 0;
    }

    @Override
    public float getFloat(final int key) {
        return 0;
    }

    @NotNull
    @Override
    public String getString(final int key) {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

}