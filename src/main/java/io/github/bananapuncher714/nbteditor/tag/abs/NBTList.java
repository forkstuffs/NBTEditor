package io.github.bananapuncher714.nbteditor.tag.abs;

import io.github.bananapuncher714.nbteditor.tag.NBTCompound;
import org.jetbrains.annotations.NotNull;

public interface NBTList extends NBTBase {

    void add(@NotNull Object value);

    void add(int key, @NotNull Object value);

    void set(int key, @NotNull Object value);

    void remove(int key);

    @NotNull
    NBTBase get(int key);

    @NotNull
    NBTCompound getCompound(int key);

    @NotNull
    NBTList getList(int key);

    short getShort(int key);

    int getInt(int key);

    @NotNull
    int[] getIntArray(int key);

    double getDouble(int key);

    float getFloat(int key);

    @NotNull
    String getString(int key);

    int size();

}
