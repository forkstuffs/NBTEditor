package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public final class NBTCompound implements NBTBase {

    @NotNull
    private final Object nbtCompound;

    @NotNull
    @Override
    public Object nbt() {
        return this.nbtCompound;
    }

    @NotNull
    @Override
    public NBTType type() {
        return NBTType.COMPOUND;
    }

}
