package io.github.bananapuncher714.nbteditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ReflectionUtil {

    @NotNull
    public Class<?> getNBTTag(@NotNull final Class<?> primitiveType) {
        return ReflectionUtil.NBTClasses.getOrDefault(primitiveType, primitiveType);
    }

    @NotNull
    public Method getMethod(@NotNull final String name) {
        return Optional.ofNullable(ReflectionUtil.methodCache.get(name)).orElseThrow(() ->
            new RuntimeException("The Method called " + name + " not found!"));
    }

    @NotNull
    public Constructor<?> getConstructor(@NotNull final Class<?> clazz) {
        return Optional.ofNullable(ReflectionUtil.constructorCache.get(clazz)).orElseThrow(() ->
            new RuntimeException("Constructor of " + clazz.getSimpleName() + " not found!"));
    }

    @NotNull
    public Class<?> getNMSClass(@NotNull final String name) {
        if (ReflectionUtil.classCache.containsKey(name)) {
            return ReflectionUtil.classCache.get(name);
        }
        try {
            return Class.forName("net.minecraft.server." + ReflectionUtil.VERSION + '.' + name);
        } catch (final ClassNotFoundException ignored) {
        }
        throw new RuntimeException("The NMS Class called " + name + " not found!");
    }

    @NotNull
    public Object getNBTVar(@NotNull final Object object) {
        final Class<?> clazz = object.getClass();
        if (ReflectionUtil.NBTTagFieldCache.containsKey(clazz)) {
            try {
                return ReflectionUtil.NBTTagFieldCache.get(clazz).get(object);
            } catch (final IllegalAccessException ignored) {
            }
        }
        throw new RuntimeException("The NBT of " + object.getClass().getSimpleName() + " not found!");
    }

}
