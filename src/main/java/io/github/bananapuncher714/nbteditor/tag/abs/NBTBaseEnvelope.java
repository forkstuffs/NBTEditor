package io.github.bananapuncher714.nbteditor.tag.abs;

import org.jetbrains.annotations.NotNull;

public abstract class NBTBaseEnvelope implements NBTBase {

    @NotNull
    private final Object nbt;

    @NotNull
    private final NBTType type;

    protected NBTBaseEnvelope(@NotNull final Object nbt, @NotNull final NBTType type) {
        this.nbt = nbt;
        this.type = type;
    }

    @NotNull
    @Override
    public final Object nbt() {
        return this.nbt;
    }

    @NotNull
    @Override
    public final NBTType type() {
        return this.type;
    }

    @Override
    public final String toString() {
        return this.nbt.toString();
    }

}
