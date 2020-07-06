package io.github.bananapuncher714.nbteditor.tag.abs;

import io.github.bananapuncher714.nbteditor.tag.NBTType;
import io.github.bananapuncher714.nbteditor.util.Reflections;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Function;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class NBTBase implements Cloneable {

    @NotNull
    private final Object nbtBase;

    @NotNull
    public final <T> T invoke(@NotNull final String methodName, @NotNull final T defaultValue,
                              @NotNull final Object... objects) {
        final Optional<Method> optional = Reflections.findMethod(this.nbtBase.getClass().getSimpleName(), methodName);
        if (!optional.isPresent()) {
            return defaultValue;
        }
        final Method method = optional.get();
        try {
            if (objects.length == 0) {
                // noinspection unchecked
                return (T) method.invoke(this.nbtBase, objects);
            } else {
                // noinspection unchecked
                return (T) method.invoke(this.nbtBase);
            }
        } catch (final IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }

    @SneakyThrows
    @NotNull
    public final Optional<Object> invokeWithoutDefault(@NotNull final String methodName,
                                                       @NotNull final Object... objects) {
        final Optional<Method> optional = Reflections.findMethod(this.nbtBase.getClass().getSimpleName(), methodName);
        if (!optional.isPresent()) {
            return Optional.empty();
        }
        final Method method = optional.get();
        if (objects.length == 0) {
            return Optional.of(method.invoke(this.nbtBase, objects));
        }
        return Optional.ofNullable(method.invoke(this.nbtBase));
    }

    @NotNull
    public final String asString() {
        return this.toString();
    }

    @NotNull
    public final Object nbt() {
        return this.nbtBase;
    }

    @Override
    public final int hashCode() {
        return this.invoke("hashCode", 0);
    }

    @Override
    public final boolean equals(final Object o) {
        return this.invoke("equals", false, o);
    }

    @SneakyThrows
    @Override
    @NotNull
    public final NBTBase clone() {
        return this.createNew().apply(this.invokeWithoutDefault("clone"));
    }

    @NotNull
    @Override
    public final String toString() {
        return this.invoke("toString", "");
    }

    @NotNull
    public abstract NBTType type();

    @NotNull
    public abstract Function<Object, NBTBase> createNew();

}
