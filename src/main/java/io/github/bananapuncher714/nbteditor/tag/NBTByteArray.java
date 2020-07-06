package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTList;
import io.github.bananapuncher714.nbteditor.util.Reflections;
import java.lang.reflect.InvocationTargetException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class NBTByteArray extends NBTList<NBTByte> {

    @NotNull
    private final Object nbtByteArray;

    @SneakyThrows
    @NotNull
    @Override
    public NBTByte get(final int key) {
        return new NBTByte(
            Reflections.findMethod("NBTByteArray", "get")
                .invoke(this.nbtByteArray, key));
    }

    @SneakyThrows
    @NotNull
    @Override
    public NBTByte set(final int key, @NotNull final NBTByte value) {
        return new NBTByte(
            Reflections.findMethod("NBTByteArray", "set")
                .invoke(this.nbtByteArray, key, value.nbt()));
    }

    @SneakyThrows
    @Override
    public void add(final int key, @NotNull final NBTByte value) {
        Reflections.findMethod("NBTByteArray", "add")
            .invoke(this.nbtByteArray, key, value.nbt());
    }

    @SneakyThrows
    @NotNull
    @Override
    public NBTByte remove(final int key) {
        return new NBTByte(
            Reflections.findMethod("NBTByteArray", "remove")
                .invoke(this.nbtByteArray, key));
    }

    @Override
    public boolean setWithType(final int key, @NotNull final NBTBase value) {
        try {
            return (boolean) Reflections.findMethod("NBTByteArray", "setWithType")
                .invoke(this.nbtByteArray, key, value.nbt());
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean completeSetWithType(final int key, @NotNull final NBTBase value) {
        try {
            return (boolean) Reflections.findMethod("NBTByteArray", "completeSetWithType")
                .invoke(this.nbtByteArray, key, value.nbt());
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    @SneakyThrows
    @Override
    public void clear() {
        Reflections.findMethod("NBTByteArray", "clear")
            .invoke(this.nbtByteArray);
    }

    @Override
    public boolean equals(final Object o) {
        try {
            return (boolean) Reflections.findMethod("NBTByteArray", "equals")
                .invoke(this.nbtByteArray, o);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public int hashCode() {
        try {
            return (int) Reflections.findMethod("NBTByteArray", "hashCode")
                .invoke(this.nbtByteArray);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int size() {
        try {
            return (int) Reflections.findMethod("NBTByteArray", "size")
                .invoke(this.nbtByteArray);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public String toString() {
        try {
            return (String) Reflections.findMethod("NBTByteArray", "toString")
                .invoke(this.nbtByteArray);
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return "";
    }

    @NotNull
    @Override
    public Object nbt() {
        return this.nbtByteArray;
    }

    @NotNull
    @Override
    public NBTType type() {
        return NBTType.BYTE_ARRAY;
    }

    @SneakyThrows
    @NotNull
    @Override
    public NBTBase clone() {
        return new NBTByteArray(
            Reflections.findMethod("NBTByteArray", "clone")
                .invoke(this.nbtByteArray));
    }

}
