package io.github.bananapuncher714.nbteditor.tag.abs;

import org.jetbrains.annotations.NotNull;

public interface NBTNumber extends NBTBase {

    long asLong();

    int asInt();

    short asShort();

    byte asByte();

    double asDouble();

    float asFloat();

    @NotNull
    Number asNumber();

}
