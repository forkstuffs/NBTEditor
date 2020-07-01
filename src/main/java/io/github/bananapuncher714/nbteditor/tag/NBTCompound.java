package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTBaseEnvelope;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.jetbrains.annotations.NotNull;

public final class NBTCompound extends NBTBaseEnvelope {

    public NBTCompound(@NotNull final Object nbt) {
        super(nbt, NBTType.COMPOUND);
    }

    public void set(@NotNull final String key, @NotNull final NBTBase value) {

    }

    public void setByte(@NotNull final String key, final byte value) {

    }

    public void setShort(@NotNull final String key, final short value) {

    }

    public void setInt(@NotNull final String key, final int value) {

    }

    public void setLong(@NotNull final String key, final long value) {

    }

    public void setDouble(@NotNull final String key, final double value) {

    }

    public void setString(@NotNull final String key, @NotNull final String value) {

    }

    public void setByteArray(@NotNull final String key, @NotNull final byte[] value) {

    }

    public void setIntArray(@NotNull final String key, @NotNull final int[] value) {

    }

    public void setIntList(@NotNull final String key, @NotNull final List<Integer> value) {

    }

    public void setLongArray(@NotNull final String key, @NotNull final long[] value) {

    }

    public void setLongList(@NotNull final String key, @NotNull final List<Long> value) {

    }

    public void setBoolean(@NotNull final String key, final boolean value) {

    }

    @NotNull
    public NBTBase get(@NotNull final String key) {
        return null;
    }

    public byte getByte(@NotNull final String key) {
        return (byte) 0;

    }

    public short getShort(@NotNull final String key) {
        return (short) 0;
    }

    public int getInt(@NotNull final String key) {
        return 0;
    }

    public long getLong(@NotNull final String key) {
        return 0L;
    }

    public float getFloat(@NotNull final String key) {
        return 0.0f;
    }

    public double getDouble(@NotNull final String key) {
        return 0.0d;
    }

    @NotNull
    public String getString(@NotNull final String key) {
        return null;
    }

    public byte[] getByteArray(@NotNull final String key) {
        return null;
    }

    public int[] getIntArray(@NotNull final String key) {
        return null;
    }

    public long[] getLongArray(@NotNull final String key) {
        return null;
    }

    @NotNull
    public NBTCompound getNBTCompound(@NotNull final String key) {
        return new NBTCompound(null);
    }

    @NotNull
    public NBTList getList(@NotNull final String key, @NotNull final NBTType value) {
        return new NBTTagList(null);
    }

    public boolean getBoolean(@NotNull final String key) {
        return false;
    }

    @NotNull
    public Set<String> keys() {
        return new HashSet<>();
    }

    public boolean has(@NotNull final String key) {
        return false;
    }

    public void remove(@NotNull final String key) {

    }

    public void setFloat(@NotNull final String key, final float value) {

    }

}