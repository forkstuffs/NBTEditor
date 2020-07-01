package io.github.bananapuncher714.nbteditor.tag.abs;

import io.github.bananapuncher714.nbteditor.tag.NBTType;
import org.jetbrains.annotations.NotNull;

public interface NBTBase {

    @NotNull
    Object nbt();

    @NotNull
    String toString();

    @NotNull
    NBTType type();

}