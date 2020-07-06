package io.github.bananapuncher714.nbteditor.tag.abs;

import io.github.bananapuncher714.nbteditor.tag.NBTType;
import org.jetbrains.annotations.NotNull;

public interface NBTBase extends Cloneable {

    default String asString() {
        return this.toString();
    }

    @NotNull
    Object nbt();

    @NotNull
    NBTType type();

    @NotNull
    NBTBase clone();

    @NotNull
    @Override
    String toString();

}