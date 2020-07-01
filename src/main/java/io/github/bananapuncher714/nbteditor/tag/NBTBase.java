package io.github.bananapuncher714.nbteditor.tag;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public abstract class NBTBase {

    @NotNull
    private final Object nbt;

}