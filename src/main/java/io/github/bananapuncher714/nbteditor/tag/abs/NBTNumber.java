package io.github.bananapuncher714.nbteditor.tag.abs;

public interface NBTNumber extends NBTBase {

    long asLong();

    int asInt();

    short asShort();

    byte asByte();

    double asDouble();

    float asFloat();

    Number asNumber();

}
