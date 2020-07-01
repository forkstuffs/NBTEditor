package io.github.bananapuncher714.nbteditor;

import io.github.bananapuncher714.nbteditor.builder.CompoundBuilder;
import io.github.bananapuncher714.nbteditor.builder.ItemStackBuilder;
import io.github.bananapuncher714.nbteditor.tag.NBTCompound;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTBase;
import io.github.bananapuncher714.nbteditor.tag.abs.NBTList;
import io.github.bananapuncher714.nbteditor.util.MinecraftVersion;
import io.github.bananapuncher714.nbteditor.util.ReflectionUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.SneakyThrows;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NBTEditorNew {

    {
        try {
            Class.forName("io.github.bananapuncher714.nbteditor.util.ReflectionUtil");
        } catch (final ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gets the Bukkit version
     *
     * @return The Bukkit version in standard package format
     */
    @NotNull
    public static String getVersion() {
        return ReflectionUtil.VERSION;
    }

    @NotNull
    public static MinecraftVersion getMinecraftVersion() {
        return ReflectionUtil.LOCAL_VERSION;
    }

    @NotNull
    public static <T, S extends CompoundBuilder<T, S>> S genericBuilder(@NotNull final T object) {
        if (object instanceof ItemStack) {
            return (S) NBTEditorNew.itemStackBuilder((ItemStack) object);
        }
        throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
    }

    @NotNull
    public static NBTCompound emptyCompound() {
        return NBTEditorNew.fromJson("{}");
    }

    @NotNull
    public static <T extends NBTBase> NBTList emptyList(@NotNull final Class<T> tClass) {

        throw new RuntimeException("Somethings wrong!");
    }

    @NotNull
    @SneakyThrows
    public static NBTCompound fromJson(@NotNull final String json) {
        return new NBTCompound(
            ReflectionUtil.getMethod("loadNBTTagCompound").invoke(null, json));
    }

    @NotNull
    public static NBTBase convertTag(@NotNull final Object object) {
//        if (object instanceof NBTBase) {
//            return (NBTBase) object;
//        }
//        if (object instanceof ItemStack) {
//            return NBTEditorNew.convertItemStackTag((ItemStack) object);
//        }
//        if (object instanceof Entity) {
//            return NBTEditorNew.convertEntityTag((Entity) object);
//        }
//        if (object instanceof Block) {
//            return NBTEditorNew.convertBlockTag((Block) object);
//        }
//        if (NBTEditorNew.getNMSClass("NBTTagCompound").isInstance(object)) {
//            return new NBTCompound(object);
//        }
//        if (NBTEditorNew.getNMSClass("NBTTagList").isInstance(object)) {
//            return new NBTCompound(object);
//        }
        throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
    }

    @NotNull
    public static ItemStackBuilder itemStackBuilder(@NotNull final ItemStack itemStack) {
        return new ItemStackBuilder(itemStack);
    }

    @Nullable
    private static String getMatch(@NotNull final String text, @NotNull final String regex) {
        final Pattern pattern = Pattern.compile(regex);
        final Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    @Nullable
    private static Object createItemStack(@NotNull final Object compound) throws IllegalAccessException,
        InvocationTargetException, InstantiationException {
        if (ReflectionUtil.LOCAL_VERSION == MinecraftVersion.v1_11 ||
            ReflectionUtil.LOCAL_VERSION == MinecraftVersion.v1_12) {
            return ReflectionUtil.getConstructor(ReflectionUtil.getNMSClass("ItemStack")).newInstance(compound);
        }
        return ReflectionUtil.getMethod("createStack").invoke(null, compound);
    }

}
