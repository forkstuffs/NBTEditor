package io.github.bananapuncher714.nbteditor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class NBTEditorNew {

    private static final Map<String, Class<?>> classCache = new HashMap<>();

    private static final Map<String, Method> methodCache = new HashMap<>();

    private static final Map<Class<?>, Constructor<?>> constructorCache = new HashMap<>();

    private static final Map<Class<?>, Class<?>> NBTClasses = new HashMap<>();

    private static final Map<Class<?>, Field> NBTTagFieldCache = new HashMap<>();

    @NotNull
    private static final String VERSION;

    @NotNull
    private static final NBTEditorNew.MinecraftVersion LOCAL_VERSION;

    @Nullable
    private static Field NBTListData;

    @Nullable
    private static Field NBTCompoundMap;

    static {
        VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        LOCAL_VERSION = Objects.requireNonNull(NBTEditorNew.MinecraftVersion.get(NBTEditorNew.VERSION));
        final String nmspath = "net.minecraft.server." + NBTEditorNew.VERSION + '.';
        final String cbpath = "org.bukkit.craftbukkit." + NBTEditorNew.VERSION + '.';
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
            NBTEditorNew.classCache.put("NBTBase", nbtBaseClass);
            NBTEditorNew.classCache.put("NBTTagCompound", nbtTagCompoundClass);
            NBTEditorNew.classCache.put("NBTTagList", nbtTagListClass);
            NBTEditorNew.classCache.put("MojangsonParser", mojansonParserClass);
            NBTEditorNew.classCache.put("ItemStack", itemStackClass);
            NBTEditorNew.classCache.put("CraftItemStack", craftItemStackClass);
            NBTEditorNew.classCache.put("CraftMetaSkull", craftMetaSkullClass);
            NBTEditorNew.classCache.put("Entity", entityClass);
            NBTEditorNew.classCache.put("CraftEntity", craftEntityClass);
            NBTEditorNew.classCache.put("EntityLiving", entityLivingClass);
            NBTEditorNew.classCache.put("CraftWorld", craftWorldClass);
            NBTEditorNew.classCache.put("CraftBlockState", craftBlockState);
            NBTEditorNew.classCache.put("BlockPosition", blockPositionClass);
            NBTEditorNew.classCache.put("TileEntity", tileEntityClass);
            NBTEditorNew.classCache.put("World", worldClass);
            NBTEditorNew.classCache.put("IBlockData", iBlockDataClass);
            NBTEditorNew.classCache.put("TileEntitySkull", tileEntitySkullClass);
            NBTEditorNew.classCache.put("GameProfile", gameProfileClass);
            NBTEditorNew.classCache.put("Property", propertyClass);
            NBTEditorNew.classCache.put("PropertyMap", propertyMapClass);
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
            NBTEditorNew.NBTClasses.put(Byte.class, nbtTagByteClass);
            NBTEditorNew.NBTClasses.put(Boolean.class, nbtTagByteClass);
            NBTEditorNew.NBTClasses.put(String.class, nbtTagStringClass);
            NBTEditorNew.NBTClasses.put(Double.class, nbtTagDoubleClass);
            NBTEditorNew.NBTClasses.put(Integer.class, nbtTagIntClass);
            NBTEditorNew.NBTClasses.put(Long.class, nbtTagLongClass);
            NBTEditorNew.NBTClasses.put(Short.class, nbtTagShortClass);
            NBTEditorNew.NBTClasses.put(Float.class, nbtTagFloatClass);
            NBTEditorNew.NBTClasses.put(byteArrayClass, nbtTagByteArrayClass);
            NBTEditorNew.NBTClasses.put(intArrayClass, nbtTagIntArrayClass);
            // Caching Methods
            NBTEditorNew.methodCache.put("get", nbtTagCompoundClass.getMethod("get", String.class));
            NBTEditorNew.methodCache.put("set", nbtTagCompoundClass.getMethod("set", String.class, nbtBaseClass));
            NBTEditorNew.methodCache.put("hasKey", nbtTagCompoundClass.getMethod("hasKey", String.class));
            NBTEditorNew.methodCache.put("setIndex", nbtTagListClass.getMethod("a", int.class, nbtBaseClass));
            if (NBTEditorNew.LOCAL_VERSION.greaterThanOrEqualTo(NBTEditorNew.MinecraftVersion.v1_14)) {
                NBTEditorNew.methodCache.put("getTypeId", nbtBaseClass.getMethod("getTypeId"));
                NBTEditorNew.methodCache.put("add", nbtTagListClass.getMethod("add", int.class, nbtBaseClass));
            } else {
                NBTEditorNew.methodCache.put("add", nbtTagListClass.getMethod("add", nbtBaseClass));
            }
            NBTEditorNew.methodCache.put("size", nbtTagListClass.getMethod("size"));
            if (NBTEditorNew.LOCAL_VERSION == NBTEditorNew.MinecraftVersion.v1_8) {
                NBTEditorNew.methodCache.put("listRemove", nbtTagListClass.getMethod("a", int.class));
            } else {
                NBTEditorNew.methodCache.put("listRemove", nbtTagListClass.getMethod("remove", int.class));
            }
            NBTEditorNew.methodCache.put("remove", nbtTagCompoundClass.getMethod("remove", String.class));
            if (NBTEditorNew.LOCAL_VERSION.greaterThanOrEqualTo(NBTEditorNew.MinecraftVersion.v1_13)) {
                NBTEditorNew.methodCache.put("getKeys", nbtTagCompoundClass.getMethod("getKeys"));
            } else {
                NBTEditorNew.methodCache.put("getKeys", nbtTagCompoundClass.getMethod("c"));
            }
            NBTEditorNew.methodCache.put("hasTag", itemStackClass.getMethod("hasTag"));
            NBTEditorNew.methodCache.put("getTag", itemStackClass.getMethod("getTag"));
            NBTEditorNew.methodCache.put("setTag", itemStackClass.getMethod("setTag", nbtTagCompoundClass));
            NBTEditorNew.methodCache.put("asNMSCopy", craftItemStackClass.getMethod("asNMSCopy", ItemStack.class));
            NBTEditorNew.methodCache.put("asBukkitCopy", craftItemStackClass.getMethod("asBukkitCopy", itemStackClass));
            NBTEditorNew.methodCache.put("getEntityHandle", craftEntityClass.getMethod("getHandle"));
            if (NBTEditorNew.LOCAL_VERSION.greaterThanOrEqualTo(NBTEditorNew.MinecraftVersion.v1_16)) {
                NBTEditorNew.methodCache.put("getEntityTag", entityClass.getMethod("save", nbtTagCompoundClass));
                NBTEditorNew.methodCache.put("setEntityTag", entityClass.getMethod("load", nbtTagCompoundClass));
            } else {
                NBTEditorNew.methodCache.put("getEntityTag", entityClass.getMethod("c", nbtTagCompoundClass));
                NBTEditorNew.methodCache.put("setEntityTag", entityClass.getMethod("f", nbtTagCompoundClass));
            }
            NBTEditorNew.methodCache.put("save", itemStackClass.getMethod("save", nbtTagCompoundClass));
            if (NBTEditorNew.LOCAL_VERSION.lessThanOrEqualTo(NBTEditorNew.MinecraftVersion.v1_10)) {
                NBTEditorNew.methodCache.put("createStack", itemStackClass.getMethod("createStack", nbtTagCompoundClass));
            } else if (NBTEditorNew.LOCAL_VERSION.greaterThanOrEqualTo(NBTEditorNew.MinecraftVersion.v1_13)) {
                NBTEditorNew.methodCache.put("createStack", itemStackClass.getMethod("a", nbtTagCompoundClass));
            }
            if (NBTEditorNew.LOCAL_VERSION.greaterThanOrEqualTo(NBTEditorNew.MinecraftVersion.v1_16)) {
                NBTEditorNew.methodCache.put("setTileTag", tileEntityClass.getMethod("load", iBlockDataClass, nbtTagCompoundClass));
                NBTEditorNew.methodCache.put("getType", worldClass.getMethod("getType", blockPositionClass));
            } else if (NBTEditorNew.LOCAL_VERSION.greaterThanOrEqualTo(NBTEditorNew.MinecraftVersion.v1_12)) {
                NBTEditorNew.methodCache.put("setTileTag", tileEntityClass.getMethod("load", nbtTagCompoundClass));
            } else {
                NBTEditorNew.methodCache.put("setTileTag", tileEntityClass.getMethod("a", nbtTagCompoundClass));
            }
            NBTEditorNew.methodCache.put("getTileEntity", worldClass.getMethod("getTileEntity", blockPositionClass));
            NBTEditorNew.methodCache.put("getWorldHandle", craftWorldClass.getMethod("getHandle"));
            NBTEditorNew.methodCache.put("setGameProfile", tileEntitySkullClass.getMethod("setGameProfile", gameProfileClass));
            NBTEditorNew.methodCache.put("getProperties", gameProfileClass.getMethod("getProperties"));
            NBTEditorNew.methodCache.put("getName", propertyClass.getMethod("getName"));
            NBTEditorNew.methodCache.put("getValue", propertyClass.getMethod("getValue"));
            NBTEditorNew.methodCache.put("values", propertyMapClass.getMethod("values"));
            NBTEditorNew.methodCache.put("put", propertyMapClass.getMethod("put", Object.class, Object.class));
            NBTEditorNew.methodCache.put("loadNBTTagCompound", mojansonParserClass.getMethod("parse", String.class));
            try {
                NBTEditorNew.methodCache.put("getTileTag", tileEntityClass.getMethod("save", nbtTagCompoundClass));
            } catch (final NoSuchMethodException exception) {
                try {
                    NBTEditorNew.methodCache.put("getTileTag", tileEntityClass.getMethod("b", nbtTagCompoundClass));
                } catch (final NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } catch (final SecurityException e) {
                e.printStackTrace();
            }
            try {
                NBTEditorNew.methodCache.put("setProfile", craftMetaSkullClass.getDeclaredMethod("setProfile", gameProfileClass));
                NBTEditorNew.methodCache.get("setProfile").setAccessible(true);
            } catch (final NoSuchMethodException ignored) {
                // The method doesn't exist, so it's before 1.15.2
            }
            // Caching Constructors
            NBTEditorNew.constructorCache.put(nbtTagByteClass, nbtTagByteClass.getDeclaredConstructor(byte.class));
            NBTEditorNew.constructorCache.put(nbtTagByteClass, nbtTagByteClass.getDeclaredConstructor(byte.class));
            NBTEditorNew.constructorCache.put(nbtTagStringClass, nbtTagStringClass.getDeclaredConstructor(String.class));
            NBTEditorNew.constructorCache.put(nbtTagDoubleClass, nbtTagDoubleClass.getDeclaredConstructor(double.class));
            NBTEditorNew.constructorCache.put(nbtTagIntClass, nbtTagIntClass.getDeclaredConstructor(int.class));
            NBTEditorNew.constructorCache.put(nbtTagLongClass, nbtTagLongClass.getDeclaredConstructor(long.class));
            NBTEditorNew.constructorCache.put(nbtTagFloatClass, nbtTagFloatClass.getDeclaredConstructor(float.class));
            NBTEditorNew.constructorCache.put(nbtTagShortClass, nbtTagShortClass.getDeclaredConstructor(short.class));
            NBTEditorNew.constructorCache.put(nbtTagByteArrayClass, nbtTagByteArrayClass.getDeclaredConstructor(byteArrayClass));
            NBTEditorNew.constructorCache.put(nbtTagIntArrayClass, nbtTagIntArrayClass.getDeclaredConstructor(intArrayClass));
            // This is for 1.15 since Mojang decided to make the constructors private
            for (final Constructor<?> cons : NBTEditorNew.constructorCache.values()) {
                cons.setAccessible(true);
            }
            NBTEditorNew.constructorCache.put(blockPositionClass,
                Objects.requireNonNull(blockPositionClass).getConstructor(int.class, int.class, int.class));
            NBTEditorNew.constructorCache.put(gameProfileClass,
                Objects.requireNonNull(gameProfileClass).getConstructor(UUID.class, String.class));
            NBTEditorNew.constructorCache.put(propertyClass,
                Objects.requireNonNull(propertyClass).getConstructor(String.class, String.class));
            if (NBTEditorNew.LOCAL_VERSION == NBTEditorNew.MinecraftVersion.v1_11 ||
                NBTEditorNew.LOCAL_VERSION == NBTEditorNew.MinecraftVersion.v1_12) {
                NBTEditorNew.constructorCache.put(itemStackClass,
                    Objects.requireNonNull(itemStackClass)
                        .getConstructor(nbtTagCompoundClass));
            }
            for (final Class<?> clazz : NBTEditorNew.NBTClasses.values()) {
                final Field data = clazz.getDeclaredField("data");
                data.setAccessible(true);
                NBTEditorNew.NBTTagFieldCache.put(clazz, data);
            }
            final Field listField = nbtTagListClass.getDeclaredField("list");
            final Field mapField = nbtTagCompoundClass.getDeclaredField("map");
            listField.setAccessible(true);
            mapField.setAccessible(true);
            NBTEditorNew.NBTListData = listField;
            NBTEditorNew.NBTCompoundMap = mapField;
        } catch (final ClassNotFoundException | NoSuchMethodException | NoSuchFieldException e) {
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
        return NBTEditorNew.VERSION;
    }

    @NotNull
    public static NBTEditorNew.MinecraftVersion getMinecraftVersion() {
        return NBTEditorNew.LOCAL_VERSION;
    }

    @NotNull
    public static <T, S extends NBTEditorNew.CompoundBuilder<T, S>> S from(@NotNull final T object) {
        if (object instanceof ItemStack) {
            return (S) NBTEditorNew.fromItemStack((ItemStack) object);
        }
        throw new IllegalArgumentException("Object provided must be of type ItemStack, Entity, Block, or NBTCompound!");
    }

    @NotNull
    public static Optional<NBTEditorNew.NBTCompound> emptyCompound() {
        return NBTEditorNew.fromJson("{}")
            .map(NBTEditorNew.NBTCompound::new);
    }

    @NotNull
    public static Optional<NBTEditorNew.NBTBase> fromJson(@NotNull final String json) {
        try {
            return Optional.of(new NBTEditorNew.NBTCompound(
                NBTEditorNew.getMethod("loadNBTTagCompound").invoke(null, json)));
        } catch (final IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @NotNull
    public static NBTEditorNew.ItemStackBuilder fromItemStack(@NotNull final ItemStack itemStack) {
        return new NBTEditorNew.ItemStackBuilder(itemStack);
    }

    @NotNull
    private static Class<?> getNBTTag(@NotNull final Class<?> primitiveType) {
        if (NBTEditorNew.NBTClasses.containsKey(primitiveType)) {
            return NBTEditorNew.NBTClasses.get(primitiveType);
        }
        return primitiveType;
    }

    @NotNull
    private static Method getMethod(@NotNull final String name) {
        final Method method = NBTEditorNew.methodCache.get(name);
        if (method == null) {
            throw new RuntimeException("The Method called " + name + " not found!");
        }
        return method;
    }

    @NotNull
    private static Constructor<?> getConstructor(@NotNull final Class<?> clazz) {
        if (NBTEditorNew.constructorCache.containsKey(clazz)) {
            return NBTEditorNew.constructorCache.get(clazz);
        }
        throw new RuntimeException("Constructor of " + clazz.getSimpleName() + " not found!");
    }

    @NotNull
    private static Class<?> getNMSClass(@NotNull final String name) {
        if (NBTEditorNew.classCache.containsKey(name)) {
            return NBTEditorNew.classCache.get(name);
        }
        try {
            return Class.forName("net.minecraft.server." + NBTEditorNew.VERSION + '.' + name);
        } catch (final ClassNotFoundException ignored) {
        }
        throw new RuntimeException("The NMS Class called " + name + " not found!");
    }

    @NotNull
    private static Object getNBTVar(@NotNull final Object object) {
        final Class<?> clazz = object.getClass();
        if (NBTEditorNew.NBTTagFieldCache.containsKey(clazz)) {
            try {
                return NBTEditorNew.NBTTagFieldCache.get(clazz).get(object);
            } catch (final IllegalAccessException ignored) {
            }
        }
        throw new RuntimeException("The NBT of " + object.getClass().getSimpleName() + " not found!");
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
        if (NBTEditorNew.LOCAL_VERSION == NBTEditorNew.MinecraftVersion.v1_11 ||
            NBTEditorNew.LOCAL_VERSION == NBTEditorNew.MinecraftVersion.v1_12) {
            return NBTEditorNew.getConstructor(NBTEditorNew.getNMSClass("ItemStack")).newInstance(compound);
        }
        return NBTEditorNew.getMethod("createStack").invoke(null, compound);
    }

    /**
     * Minecraft variables as enums
     *
     * @author BananaPuncher714
     */
    @RequiredArgsConstructor
    private enum MinecraftVersion {
        v1_8("1_8", 0),
        v1_9("1_9", 1),
        v1_10("1_10", 2),
        v1_11("1_11", 3),
        v1_12("1_12", 4),
        v1_13("1_13", 5),
        v1_14("1_14", 6),
        v1_15("1_15", 7),
        v1_16("1_16", 8),
        v1_17("1_17", 9),
        v1_18("1_18", 10),
        v1_19("1_19", 11);

        @NotNull
        private final String key;

        private final int order;

        @Nullable
        public static NBTEditorNew.MinecraftVersion get(@NotNull final String text) {
            return Arrays.stream(NBTEditorNew.MinecraftVersion.values())
                .filter(version -> text.contains(version.key))
                .findFirst()
                .orElse(null);
        }

        // Would be really cool if we could overload operators here
        public boolean greaterThanOrEqualTo(@NotNull final NBTEditorNew.MinecraftVersion other) {
            return this.order >= other.order;
        }

        public boolean lessThanOrEqualTo(@NotNull final NBTEditorNew.MinecraftVersion other) {
            return this.order <= other.order;
        }
    }

    public interface NBTBase {

    }

    public static class ItemStackBuilder extends NBTEditorNew.CompoundBuilder<ItemStack, NBTEditorNew.ItemStackBuilder> {

        public ItemStackBuilder(@NotNull final ItemStack object) {
            super(object);
        }

        @NotNull
        @Override
        public Optional<NBTEditorNew.NBTBase> getTag(@NotNull final String... key) {

            return Optional.empty();
        }

        @NotNull
        @Override
        public NBTEditorNew.ItemStackBuilder setTag(@NotNull final NBTEditorNew.NBTBase nbt,
                                                    @NotNull final String... key) {

            return this.self();
        }

        @NotNull
        @Override
        public NBTEditorNew.ItemStackBuilder self() {
            return this;
        }

    }

    @RequiredArgsConstructor
    public abstract static class CompoundBuilder<T, S extends NBTEditorNew.CompoundBuilder<T, S>> {

        @NotNull
        private final T object;

        @NotNull
        public final S setTagIfAbsent(@NotNull final String nbt, @NotNull final String... key) {
            return NBTEditorNew.fromJson(nbt)
                .map(nbtBase -> this.setTagIfAbsent(nbtBase, key))
                .orElseGet(this::self);
        }

        @NotNull
        public final S setTagIfAbsent(@NotNull final NBTEditorNew.NBTBase nbt, @NotNull final String... key) {
            if (!this.getTag(key).isPresent()) {
                return this.setTag(nbt, key);
            }
            return this.self();
        }

        @NotNull
        public final NBTEditorNew.NBTBase getTagOrEmpty(@NotNull final String... key) {
            return this.getTag(key)
                .orElse(new NBTEditorNew.NBTCompound(NBTEditorNew.fromJson("{}")));
        }

        @NotNull
        public abstract Optional<NBTEditorNew.NBTBase> getTag(@NotNull String... key);

        @NotNull
        public abstract S setTag(@NotNull NBTEditorNew.NBTBase nbtBase, @NotNull String... key);

        @NotNull
        public abstract S self();

    }

    @RequiredArgsConstructor
    public static final class NBTCompound implements NBTEditorNew.NBTBase {

        @NotNull
        private final Object nbtTagCompound;

    }

}
