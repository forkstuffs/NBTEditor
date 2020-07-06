package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTNumber;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public final class NBTByte extends NBTNumber {

    public NBTByte(@NotNull final Object nbtBase) {
        super(nbtBase);
    }

    @Override
    public long asLong() {
        return this.invoke("asLong", 0L);
    }

    @Override
    public int asInt() {
        return this.invoke("asInt", 0);
    }

    @Override
    public short asShort() {
        return this.invoke("asShort", (short) 0);
    }

    @Override
    public byte asByte() {
        return this.invoke("asByte", (byte) 0);
    }

    @Override
    public double asDouble() {
        return this.invoke("asDouble", 0.0d);
    }

    @Override
    public float asFloat() {
        return this.invoke("asFloat", 0.0f);
    }

    @NotNull
    @Override
    public Number asNumber() {
        return this.invoke("asNumber", 0);
    }

    @NotNull
    @Override
    public NBTType type() {
        return NBTType.BYTE;
    }

    @NotNull
    @Override
    public Function<Object, NBTBase> createNew() {
        return NBTByte::new;
    }

}
