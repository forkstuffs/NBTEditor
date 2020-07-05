package io.github.bananapuncher714.nbteditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;

@UtilityClass
public class ReflectionUtil {

    static {
        try {
            // Caching Methods
            ReflectionUtil.methodCache.put("getWorldHandle", craftWorldClass.getMethod("getHandle"));
            ReflectionUtil.methodCache.put("setGameProfile", tileEntitySkullClass.getMethod("setGameProfile", gameProfileClass));
            ReflectionUtil.methodCache.put("getProperties", gameProfileClass.getMethod("getProperties"));
            ReflectionUtil.methodCache.put("getName", propertyClass.getMethod("getName"));
            ReflectionUtil.methodCache.put("getValue", propertyClass.getMethod("getValue"));
            ReflectionUtil.methodCache.put("values", propertyMapClass.getMethod("values"));
            ReflectionUtil.methodCache.put("put", propertyMapClass.getMethod("put", Object.class, Object.class));
            try {
                ReflectionUtil.methodCache.put("getTileTag", tileEntityClass.getMethod("save", nbtTagCompoundClass));
            } catch (final NoSuchMethodException exception) {
                try {
                    ReflectionUtil.methodCache.put("getTileTag", tileEntityClass.getMethod("b", nbtTagCompoundClass));
                } catch (final NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } catch (final SecurityException e) {
                e.printStackTrace();
            }
            // Caching Constructors
            ReflectionUtil.constructorCache.put(nbtTagByteClass, nbtTagByteClass.getDeclaredConstructor(byte.class));
            ReflectionUtil.constructorCache.put(nbtTagByteClass, nbtTagByteClass.getDeclaredConstructor(byte.class));
            ReflectionUtil.constructorCache.put(nbtTagStringClass, nbtTagStringClass.getDeclaredConstructor(String.class));
            ReflectionUtil.constructorCache.put(nbtTagDoubleClass, nbtTagDoubleClass.getDeclaredConstructor(double.class));
            ReflectionUtil.constructorCache.put(nbtTagIntClass, nbtTagIntClass.getDeclaredConstructor(int.class));
            ReflectionUtil.constructorCache.put(nbtTagLongClass, nbtTagLongClass.getDeclaredConstructor(long.class));
            ReflectionUtil.constructorCache.put(nbtTagFloatClass, nbtTagFloatClass.getDeclaredConstructor(float.class));
            ReflectionUtil.constructorCache.put(nbtTagShortClass, nbtTagShortClass.getDeclaredConstructor(short.class));
            ReflectionUtil.constructorCache.put(nbtTagByteArrayClass, nbtTagByteArrayClass.getDeclaredConstructor(byteArrayClass));
            ReflectionUtil.constructorCache.put(nbtTagIntArrayClass, nbtTagIntArrayClass.getDeclaredConstructor(intArrayClass));
            // This is for 1.15 since Mojang decided to make the constructors private
            for (final Constructor<?> cons : ReflectionUtil.constructorCache.values()) {
                cons.setAccessible(true);
            }
            ReflectionUtil.constructorCache.put(blockPositionClass,
                Objects.requireNonNull(blockPositionClass).getConstructor(int.class, int.class, int.class));
            ReflectionUtil.constructorCache.put(gameProfileClass,
                Objects.requireNonNull(gameProfileClass).getConstructor(UUID.class, String.class));
            ReflectionUtil.constructorCache.put(propertyClass,
                Objects.requireNonNull(propertyClass).getConstructor(String.class, String.class));
            for (final Class<?> clazz : ReflectionUtil.NBTClasses.values()) {
                final Field data = clazz.getDeclaredField("data");
                data.setAccessible(true);
                ReflectionUtil.NBTTagFieldCache.put(clazz, data);
            }
            ReflectionUtil.NBTListData = listField;
            ReflectionUtil.NBTCompoundMap = mapField;
        } catch (final ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

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
