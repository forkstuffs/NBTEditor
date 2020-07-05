package io.github.bananapuncher714.nbteditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Optional;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@RequiredArgsConstructor
public final class Reference {

    @NotNull
    @Getter
    private final String key;

    @NotNull
    @Getter
    private final Class<?> aClass;

    @Nullable
    private final Constructor<?> constructor;

    @NotNull
    private final Map<String, Method> methods;

    @NotNull
    private final Map<String, Field> fields;

    @NotNull
    public Constructor<?> getConstructor() {
        return Optional.ofNullable(this.constructor).orElseThrow(() ->
            new RuntimeException("There is no registed constructor for " + this.key + " in " + this.aClass.getSimpleName() + " class!"));
    }

    @NotNull
    public Method getMethod(@NotNull final String name) {
        return Optional.ofNullable(this.methods.get(name)).orElseThrow(() ->
            new RuntimeException("The method of key called " + this.key + " not found in " + this.aClass.getSimpleName() + " class!"));
    }

    @NotNull
    public Field getField(@NotNull final String name) {
        return Optional.ofNullable(this.fields.get(name)).orElseThrow(() ->
            new RuntimeException("The field called " + name + " of key called " + this.key + " not found in " + this.aClass.getSimpleName() + " class!"));
    }

}