package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTNumber;
import io.github.bananapuncher714.nbteditor.util.Reflections;
import java.lang.reflect.InvocationTargetException;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class NBTTagByte implements NBTNumber {

    @NotNull
    private final Object nbtTagByte;

    @Override
    public long asLong() {
        try {
            return (long) Reflections.findMethod("NBTTagByte", "asLong").invoke(this.nbtTagByte);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0L;
    }

    @Override
    public int asInt() {
        try {
            return (int) Reflections.findMethod("NBTTagByte", "asInt").invoke(this.nbtTagByte);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public short asShort() {
        try {
            return (short) Reflections.findMethod("NBTTagByte", "asShort").invoke(this.nbtTagByte);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return (short) 0;
    }

    @Override
    public byte asByte() {
        try {
            return (byte) Reflections.findMethod("NBTTagByte", "asByte").invoke(this.nbtTagByte);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return (byte) 0;
    }

    @Override
    public double asDouble() {
        try {
            return (double) Reflections.findMethod("NBTTagByte", "asDouble").invoke(this.nbtTagByte);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0.0d;
    }

    @Override
    public float asFloat() {
        try {
            return (float) Reflections.findMethod("NBTTagByte", "asFloat").invoke(this.nbtTagByte);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0.0f;
    }

    @NotNull
    @Override
    public Number asNumber() {
        try {
            return (Number) Reflections.findMethod("NBTTagByte", "asNumber").invoke(this.nbtTagByte);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @NotNull
    @Override
    public Object nbt() {
        return this.nbtTagByte;
    }

    @NotNull
    @Override
    public NBTType type() {
        return NBTType.BYTE;
    }

}