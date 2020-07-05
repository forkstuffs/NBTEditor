package io.github.bananapuncher714.nbteditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class Reference {

    @NotNull
    private final String key;

    @NotNull
    private final Class<?> aClass;

    @Nullable
    private final Constructor<?> constructors;

    @NotNull
    private final Map<String, Method> methods;

    @NotNull
    private final Map<String, Field> fields;

    @NotNull
    public Optional<Constructor<?>> getConstructors() {
        return Optional.ofNullable(this.constructors);
    }

}