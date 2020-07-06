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
        return 0;
    }

    @Override
    public int asInt() {
        return 0;
    }

    @Override
    public short asShort() {
        return 0;
    }

    @Override
    public byte asByte() {
        return 0;
    }

    @Override
    public double asDouble() {
        return 0;
    }

    @Override
    public float asFloat() {
        return 0;
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