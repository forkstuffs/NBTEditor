package io.github.bananapuncher714.nbteditor.tag;

import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public final class NBTCompound extends NBTBase {

    public NBTCompound(@NotNull final Object nbtBase) {
        super(nbtBase);
    }

    @NotNull
    @Override
    public NBTType type() {
        return NBTType.COMPOUND;
    }

    @NotNull
    @Override
    public Function<Object, NBTBase> createNew() {
        return NBTCompound::new;
    }

}
