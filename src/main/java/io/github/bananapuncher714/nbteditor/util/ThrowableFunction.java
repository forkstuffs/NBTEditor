package io.github.bananapuncher714.nbteditor.util;

import org.jetbrains.annotations.NotNull;

public interface ThrowableFunction<T, R> {

    @NotNull
    R apply(@NotNull T t) throws Exception;

}
