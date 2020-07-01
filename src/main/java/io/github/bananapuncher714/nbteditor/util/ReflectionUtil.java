package io.github.bananapuncher714.nbteditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class ReflectionUtil {

    public final Map<String, Class<?>> classCache = new HashMap<>();

    public final Map<String, Method> methodCache = new HashMap<>();

    public final Map<Class<?>, Constructor<?>> constructorCache = new HashMap<>();

    public final Map<Class<?>, Class<?>> NBTClasses = new HashMap<>();

    public final Map<Class<?>, Field> NBTTagFieldCache = new HashMap<>();

    @NotNull
    public final MinecraftVersion LOCAL_VERSION;

    @NotNull
    public final String VERSION;

    @Nullable
    public Field NBTListData;

    @Nullable
    public Field NBTCompoundMap;

    static {
        VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        LOCAL_VERSION = Objects.requireNonNull(MinecraftVersion.get(ReflectionUtil.VERSION));
        final String nmspath = "net.minecraft.server." + ReflectionUtil.VERSION + '.';
        final String cbpath = "org.bukkit.craftbukkit." + ReflectionUtil.VERSION + '.';
        final String authpath = "com.mojang.authlib.";
        try {
            // Caching NMS|CB|MOJANG Classes
            final Class<?> nbtBaseClass = Class.forName(nmspath + "NBTBase");
            final Class<?> nbtTagCompoundClass = Class.forName(nmspath + "NBTTagCompound");
            final Class<?> nbtTagListClass = Class.forName(nmspath + "NBTTagList");
            final Class<?> mojansonParserClass = Class.forName(nmspath + "MojangsonParser");
            final Class<?> itemStackClass = Class.forName(nmspath + "ItemStack");
            final Class<?> craftItemStackClass = Class.forName(cbpath + "inventory." + "CraftItemStack");
            final Class<?> craftMetaSkullClass = Class.forName(cbpath + "inventory." + "CraftMetaSkull");
            final Class<?> entityClass = Class.forName(nmspath + "Entity");
            final Class<?> craftEntityClass = Class.forName(cbpath + "entity." + "CraftEntity");
            final Class<?> entityLivingClass = Class.forName(nmspath + "EntityLiving");
            final Class<?> craftWorldClass = Class.forName(cbpath + "CraftWorld");
            final Class<?> craftBlockState = Class.forName(cbpath + "block." + "CraftBlockState");
            final Class<?> blockPositionClass = Class.forName(nmspath + "BlockPosition");
            final Class<?> tileEntityClass = Class.forName(nmspath + "TileEntity");
            final Class<?> worldClass = Class.forName(nmspath + "World");
            final Class<?> iBlockDataClass = Class.forName(nmspath + "IBlockData");
            final Class<?> tileEntitySkullClass = Class.forName(nmspath + "TileEntitySkull");
            final Class<?> gameProfileClass = Class.forName(authpath + "GameProfile");
            final Class<?> propertyClass = Class.forName(authpath + "properties.Property");
            final Class<?> propertyMapClass = Class.forName(authpath + "properties.PropertyMap");
            ReflectionUtil.classCache.put("NBTBase", nbtBaseClass);
            ReflectionUtil.classCache.put("NBTTagCompound", nbtTagCompoundClass);
            ReflectionUtil.classCache.put("NBTTagList", nbtTagListClass);
            ReflectionUtil.classCache.put("MojangsonParser", mojansonParserClass);
            ReflectionUtil.classCache.put("ItemStack", itemStackClass);
            ReflectionUtil.classCache.put("CraftItemStack", craftItemStackClass);
            ReflectionUtil.classCache.put("CraftMetaSkull", craftMetaSkullClass);
            ReflectionUtil.classCache.put("Entity", entityClass);
            ReflectionUtil.classCache.put("CraftEntity", craftEntityClass);
            ReflectionUtil.classCache.put("EntityLiving", entityLivingClass);
            ReflectionUtil.classCache.put("CraftWorld", craftWorldClass);
            ReflectionUtil.classCache.put("CraftBlockState", craftBlockState);
            ReflectionUtil.classCache.put("BlockPosition", blockPositionClass);
            ReflectionUtil.classCache.put("TileEntity", tileEntityClass);
            ReflectionUtil.classCache.put("World", worldClass);
            ReflectionUtil.classCache.put("IBlockData", iBlockDataClass);
            ReflectionUtil.classCache.put("TileEntitySkull", tileEntitySkullClass);
            ReflectionUtil.classCache.put("GameProfile", gameProfileClass);
            ReflectionUtil.classCache.put("Property", propertyClass);
            ReflectionUtil.classCache.put("PropertyMap", propertyMapClass);
            // Caching NBT Classes
            final Class<?> nbtTagByteClass = Class.forName(nmspath + "NBTTagByte");
            final Class<?> nbtTagStringClass = Class.forName(nmspath + "NBTTagString");
            final Class<?> nbtTagDoubleClass = Class.forName(nmspath + "NBTTagDouble");
            final Class<?> nbtTagIntClass = Class.forName(nmspath + "NBTTagInt");
            final Class<?> nbtTagLongClass = Class.forName(nmspath + "NBTTagLong");
            final Class<?> nbtTagShortClass = Class.forName(nmspath + "NBTTagShort");
            final Class<?> nbtTagFloatClass = Class.forName(nmspath + "NBTTagFloat");
            final Class<?> nbtTagByteArrayClass = Class.forName(nmspath + "NBTTagByteArray");
            final Class<?> nbtTagIntArrayClass = Class.forName(nmspath + "NBTTagIntArray");
            final Class<?> byteArrayClass = Class.forName("[B");
            final Class<?> intArrayClass = Class.forName("[I");
            ReflectionUtil.NBTClasses.put(Byte.class, nbtTagByteClass);
            ReflectionUtil.NBTClasses.put(Boolean.class, nbtTagByteClass);
            ReflectionUtil.NBTClasses.put(String.class, nbtTagStringClass);
            ReflectionUtil.NBTClasses.put(Double.class, nbtTagDoubleClass);
            ReflectionUtil.NBTClasses.put(Integer.class, nbtTagIntClass);
            ReflectionUtil.NBTClasses.put(Long.class, nbtTagLongClass);
            ReflectionUtil.NBTClasses.put(Short.class, nbtTagShortClass);
            ReflectionUtil.NBTClasses.put(Float.class, nbtTagFloatClass);
            ReflectionUtil.NBTClasses.put(byteArrayClass, nbtTagByteArrayClass);
            ReflectionUtil.NBTClasses.put(intArrayClass, nbtTagIntArrayClass);
            // Caching Methods
            ReflectionUtil.methodCache.put("get", nbtTagCompoundClass.getMethod("get", String.class));
            ReflectionUtil.methodCache.put("set", nbtTagCompoundClass.getMethod("set", String.class, nbtBaseClass));
            ReflectionUtil.methodCache.put("hasKey", nbtTagCompoundClass.getMethod("hasKey", String.class));
            ReflectionUtil.methodCache.put("setIndex", nbtTagListClass.getMethod("a", int.class, nbtBaseClass));
            if (ReflectionUtil.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_14)) {
                ReflectionUtil.methodCache.put("getTypeId", nbtBaseClass.getMethod("getTypeId"));
                ReflectionUtil.methodCache.put("add", nbtTagListClass.getMethod("add", int.class, nbtBaseClass));
            } else {
                ReflectionUtil.methodCache.put("add", nbtTagListClass.getMethod("add", nbtBaseClass));
            }
            ReflectionUtil.methodCache.put("size", nbtTagListClass.getMethod("size"));
            if (ReflectionUtil.LOCAL_VERSION == MinecraftVersion.v1_8) {
                ReflectionUtil.methodCache.put("listRemove", nbtTagListClass.getMethod("a", int.class));
            } else {
                ReflectionUtil.methodCache.put("listRemove", nbtTagListClass.getMethod("remove", int.class));
            }
            ReflectionUtil.methodCache.put("remove", nbtTagCompoundClass.getMethod("remove", String.class));
            if (ReflectionUtil.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                ReflectionUtil.methodCache.put("getKeys", nbtTagCompoundClass.getMethod("getKeys"));
            } else {
                ReflectionUtil.methodCache.put("getKeys", nbtTagCompoundClass.getMethod("c"));
            }
            ReflectionUtil.methodCache.put("hasTag", itemStackClass.getMethod("hasTag"));
            ReflectionUtil.methodCache.put("getTag", itemStackClass.getMethod("getTag"));
            ReflectionUtil.methodCache.put("setTag", itemStackClass.getMethod("setTag", nbtTagCompoundClass));
            ReflectionUtil.methodCache.put("asNMSCopy", craftItemStackClass.getMethod("asNMSCopy", ItemStack.class));
            ReflectionUtil.methodCache.put("asBukkitCopy", craftItemStackClass.getMethod("asBukkitCopy", itemStackClass));
            ReflectionUtil.methodCache.put("getEntityHandle", craftEntityClass.getMethod("getHandle"));
            if (ReflectionUtil.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                ReflectionUtil.methodCache.put("getEntityTag", entityClass.getMethod("save", nbtTagCompoundClass));
                ReflectionUtil.methodCache.put("setEntityTag", entityClass.getMethod("load", nbtTagCompoundClass));
            } else {
                ReflectionUtil.methodCache.put("getEntityTag", entityClass.getMethod("c", nbtTagCompoundClass));
                ReflectionUtil.methodCache.put("setEntityTag", entityClass.getMethod("f", nbtTagCompoundClass));
            }
            ReflectionUtil.methodCache.put("save", itemStackClass.getMethod("save", nbtTagCompoundClass));
            if (ReflectionUtil.LOCAL_VERSION.lessThanOrEqualTo(MinecraftVersion.v1_10)) {
                ReflectionUtil.methodCache.put("createStack", itemStackClass.getMethod("createStack", nbtTagCompoundClass));
            } else if (ReflectionUtil.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                ReflectionUtil.methodCache.put("createStack", itemStackClass.getMethod("a", nbtTagCompoundClass));
            }
            if (ReflectionUtil.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                ReflectionUtil.methodCache.put("setTileTag", tileEntityClass.getMethod("load", iBlockDataClass, nbtTagCompoundClass));
                ReflectionUtil.methodCache.put("getType", worldClass.getMethod("getType", blockPositionClass));
            } else if (ReflectionUtil.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_12)) {
                ReflectionUtil.methodCache.put("setTileTag", tileEntityClass.getMethod("load", nbtTagCompoundClass));
            } else {
                ReflectionUtil.methodCache.put("setTileTag", tileEntityClass.getMethod("a", nbtTagCompoundClass));
            }
            ReflectionUtil.methodCache.put("getTileEntity", worldClass.getMethod("getTileEntity", blockPositionClass));
            ReflectionUtil.methodCache.put("getWorldHandle", craftWorldClass.getMethod("getHandle"));
            ReflectionUtil.methodCache.put("setGameProfile", tileEntitySkullClass.getMethod("setGameProfile", gameProfileClass));
            ReflectionUtil.methodCache.put("getProperties", gameProfileClass.getMethod("getProperties"));
            ReflectionUtil.methodCache.put("getName", propertyClass.getMethod("getName"));
            ReflectionUtil.methodCache.put("getValue", propertyClass.getMethod("getValue"));
            ReflectionUtil.methodCache.put("values", propertyMapClass.getMethod("values"));
            ReflectionUtil.methodCache.put("put", propertyMapClass.getMethod("put", Object.class, Object.class));
            ReflectionUtil.methodCache.put("loadNBTTagCompound", mojansonParserClass.getMethod("parse", String.class));
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
            try {
                ReflectionUtil.methodCache.put("setProfile", craftMetaSkullClass.getDeclaredMethod("setProfile", gameProfileClass));
                ReflectionUtil.methodCache.get("setProfile").setAccessible(true);
            } catch (final NoSuchMethodException ignored) {
                // The method doesn't exist, so it's before 1.15.2
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
            if (ReflectionUtil.LOCAL_VERSION == MinecraftVersion.v1_11 ||
                ReflectionUtil.LOCAL_VERSION == MinecraftVersion.v1_12) {
                ReflectionUtil.constructorCache.put(itemStackClass,
                    Objects.requireNonNull(itemStackClass)
                        .getConstructor(nbtTagCompoundClass));
            }
            for (final Class<?> clazz : ReflectionUtil.NBTClasses.values()) {
                final Field data = clazz.getDeclaredField("data");
                data.setAccessible(true);
                ReflectionUtil.NBTTagFieldCache.put(clazz, data);
            }
            final Field listField = nbtTagListClass.getDeclaredField("list");
            final Field mapField = nbtTagCompoundClass.getDeclaredField("map");
            listField.setAccessible(true);
            mapField.setAccessible(true);
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
