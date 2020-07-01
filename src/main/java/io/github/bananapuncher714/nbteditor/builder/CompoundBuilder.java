package io.github.bananapuncher714.nbteditor.builder;

import io.github.bananapuncher714.nbteditor.NBTEditorNew;
import io.github.bananapuncher714.nbteditor.tag.NBTBase;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public abstract class CompoundBuilder<T, S extends CompoundBuilder<T, S>> {

    @NotNull
    protected final T object;

    @NotNull
    public final S setTagIfAbsent(@NotNull final Object nbt, @NotNull final String... key) {
        return this.setTagIfAbsent(NBTEditorNew.convertTag(nbt), key);
    }

    @NotNull
    public final S setTagIfAbsent(@NotNull final NBTBase nbt, @NotNull final String... key) {
        if (!this.getTag(key).isPresent()) {
            return this.setTag(nbt, key);
        }
        return this.self();
    }

    @NotNull
    public final NBTBase getTagOrEmpty(@NotNull final String... key) {
        return this.getTag(key)
            .orElse(NBTEditorNew.emptyCompound());
    }

    @NotNull
    public final Optional<NBTBase> getTag(@NotNull final String... key) {
        return Optional.empty();
    }

    @NotNull
    public final S setTag(@NotNull final Object object, @NotNull final String... key) {
        return this.setTag(NBTEditorNew.convertTag(object), key);
    }

    @NotNull
    public final S setTag(@NotNull final NBTBase nbt, @NotNull final String... key) {
        return this.createNew(this.object);
    }

    @NotNull
    public final T build() {
        return this.object;
    }

    @NotNull
    public abstract S self();

    @NotNull
    public abstract S createNew(@NotNull T t);

}