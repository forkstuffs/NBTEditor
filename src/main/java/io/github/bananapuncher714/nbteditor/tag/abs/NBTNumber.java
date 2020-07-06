package io.github.bananapuncher714.nbteditor.tag.abs;

import org.jetbrains.annotations.NotNull;

public abstract class NBTNumber extends NBTBase {

    protected NBTNumber(@NotNull final Object nbtBase) {
        super(nbtBase);
    }

    public abstract long asLong();

    public abstract int asInt();

    public abstract short asShort();

    public abstract byte asByte();

    public abstract double asDouble();

    public abstract float asFloat();

    @NotNull
    public abstract Number asNumber();

}
