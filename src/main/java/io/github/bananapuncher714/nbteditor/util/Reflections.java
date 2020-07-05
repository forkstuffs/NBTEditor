package io.github.bananapuncher714.nbteditor.util;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@UtilityClass
public class Reflections {

    @NotNull
    public final MinecraftVersion LOCAL_VERSION;

    @NotNull
    public final String VERSION;

    private final String nmspath = "net.minecraft.server." + Reflections.VERSION + '.';

    private final String cbpath = "org.bukkit.craftbukkit." + Reflections.VERSION + '.';

    private final String authpath = "com.mojang.authlib.";

    private final Map<String, Reflections.Reference> REF = new HashMap<>();

    static {
        VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        LOCAL_VERSION = Objects.requireNonNull(MinecraftVersion.get(Reflections.VERSION));
        try {
            // Caching NMS|CB|MOJANG Classes
            final Class<?> nbtBaseClass = Reflections.findNMSClass("NBTBase");
            final Class<?> nbtTagCompoundClass = Reflections.findNMSClass("NBTTagCompound");
            final Class<?> nbtTagListClass = Reflections.findNMSClass("NBTTagList");
            final Class<?> mojansonParserClass = Reflections.findNMSClass("MojangsonParser");
            final Class<?> itemStackClass = Reflections.findNMSClass("ItemStack");
            final Class<?> craftItemStackClass = Reflections.findCBClass("inventory.CraftItemStack");
            final Class<?> craftMetaSkullClass = Reflections.findCBClass("inventory.CraftMetaSkull");
            final Class<?> entityClass = Reflections.findNMSClass("Entity");
            final Class<?> craftEntityClass = Reflections.findCBClass("entity.CraftEntity");
            final Class<?> entityLivingClass = Reflections.findNMSClass("EntityLiving");
            final Class<?> craftWorldClass = Reflections.findCBClass("CraftWorld");
            final Class<?> craftBlockState = Reflections.findCBClass("block." + "CraftBlockState");
            final Class<?> blockPositionClass = Reflections.findNMSClass("BlockPosition");
            final Class<?> tileEntityClass = Reflections.findNMSClass("TileEntity");
            final Class<?> worldClass = Reflections.findNMSClass("World");
            final Class<?> iBlockDataClass = Reflections.findNMSClass("IBlockData");
            final Class<?> tileEntitySkullClass = Reflections.findNMSClass("TileEntitySkull");
            final Class<?> gameProfileClass = Reflections.findAuthClass("GameProfile");
            final Class<?> propertyClass = Reflections.findAuthClass("properties.Property");
            final Class<?> propertyMapClass = Reflections.findAuthClass("properties.PropertyMap");

            // Caching NBT Classes
            final Class<?> nbtTagByteClass = Reflections.findNMSClass("NBTTagByte");
            final Class<?> nbtTagStringClass = Reflections.findNMSClass("NBTTagString");
            final Class<?> nbtTagDoubleClass = Reflections.findNMSClass("NBTTagDouble");
            final Class<?> nbtTagIntClass = Reflections.findNMSClass("NBTTagInt");
            final Class<?> nbtTagLongClass = Reflections.findNMSClass("NBTTagLong");
            final Class<?> nbtTagShortClass = Reflections.findNMSClass("NBTTagShort");
            final Class<?> nbtTagFloatClass = Reflections.findNMSClass("NBTTagFloat");
            final Class<?> nbtTagByteArrayClass = Reflections.findNMSClass("NBTTagByteArray");
            final Class<?> nbtTagIntArrayClass = Reflections.findNMSClass("NBTTagIntArray");
            final Class<?> byteArrayClass = Reflections.findClass("[B");
            final Class<?> intArrayClass = Reflections.findClass("[I");

            // Caching Methods
            final Map<String, Method> nbtBaseClassMethods = Reflections.cacheMethods(nbtBaseClass, aClass ->
                Collections.singletonMap("getTypeId", nbtBaseClass.getMethod("getTypeId")));
            final Map<String, Method> nbtTagCompoundClassMethods = Reflections.cacheMethods(nbtTagCompoundClass, aClass -> {
                final Map<String, Method> methods = new HashMap<>();
                methods.put("get", aClass.getMethod("get", String.class));
                methods.put("set", aClass.getMethod("set", String.class, nbtBaseClass));
                methods.put("hasKey", aClass.getMethod("hasKey", String.class));
                methods.put("remove", aClass.getMethod("remove", String.class));
                if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                    methods.put("getKeys", aClass.getMethod("getKeys"));
                } else {
                    methods.put("getKeys", aClass.getMethod("c"));
                }
                return methods;
            });
            final Map<String, Method> mojansonParserClassMethods = Reflections.cacheMethods(mojansonParserClass, aClass ->
                Collections.singletonMap("parse", aClass.getMethod("parse", String.class)));
            final Map<String, Method> itemStackClassMethods = Reflections.cacheMethods(itemStackClass, aClass -> {
                final Map<String, Method> methods = new HashMap<>();
                methods.put("hasTag", aClass.getMethod("hasTag"));
                methods.put("getTag", aClass.getMethod("getTag"));
                methods.put("setTag", aClass.getMethod("setTag", nbtTagCompoundClass));
                methods.put("save", aClass.getMethod("save", nbtTagCompoundClass));
                if (Reflections.LOCAL_VERSION.lessThanOrEqualTo(MinecraftVersion.v1_10)) {
                    methods.put("createStack", aClass.getMethod("createStack", nbtTagCompoundClass));
                } else if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                    methods.put("createStack", aClass.getMethod("a", nbtTagCompoundClass));
                }
                return methods;
            });
            final Map<String, Method> craftItemStackClassMethods = Reflections.cacheMethods(craftItemStackClass, aClass -> {
                final Map<String, Method> methods = new HashMap<>();
                methods.put("asNMSCopy", aClass.getMethod("asNMSCopy", ItemStack.class));
                methods.put("asBukkitCopy", aClass.getMethod("asBukkitCopy", itemStackClass));
                return methods;
            });
            final Map<String, Method> craftMetaSkullClassMethods = new HashMap<>();
            try {
                craftMetaSkullClassMethods.putAll(Reflections.cacheMethods(craftMetaSkullClass, aClass ->
                    Collections.singletonMap("setProfile", craftMetaSkullClass.getDeclaredMethod("setProfile", gameProfileClass))));
            } catch (final NoSuchMethodException ignored) {
                // The method doesn't exist, so it's before 1.15.2
            }
            final Map<String, Method> entityClassMethods = Reflections.cacheMethods(entityClass, aClass -> {
                final Map<String, Method> methods = new HashMap<>();
                if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                    methods.put("save", aClass.getMethod("save", nbtTagCompoundClass));
                    methods.put("load", aClass.getMethod("load", nbtTagCompoundClass));
                } else {
                    methods.put("save", aClass.getMethod("c", nbtTagCompoundClass));
                    methods.put("load", aClass.getMethod("f", nbtTagCompoundClass));
                }
                return methods;
            });
            final Map<String, Method> craftEntityClassMethods = Reflections.cacheMethods(craftEntityClass, aClass ->
                Collections.singletonMap("getHandle", aClass.getMethod("getHandle")));
            Reflections.cacheMethods(tileEntityClass, aClass -> {
                final Map<String, Method> methods = new HashMap<>();
                if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                    methods.put("load", aClass.getMethod("load", iBlockDataClass, nbtTagCompoundClass));
                } else if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_12)) {
                    methods.put("load", aClass.getMethod("load", nbtTagCompoundClass));
                } else {
                    methods.put("load", aClass.getMethod("a", nbtTagCompoundClass));
                }
                try {
                    methods.put("save", aClass.getMethod("save", nbtTagCompoundClass));
                } catch (final NoSuchMethodException exception) {
                    try {
                        methods.put("save", aClass.getMethod("b", nbtTagCompoundClass));
                    } catch (final NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                } catch (final SecurityException e) {
                    e.printStackTrace();
                }
                return methods;
            });
            Reflections.cacheMethods(worldClass, aClass -> {
                final Map<String, Method> methods = new HashMap<>();
                if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                    methods.put("getType", aClass.getMethod("getType", blockPositionClass));
                }
                methods.put("getTileEntity", aClass.getMethod("getTileEntity", blockPositionClass));
                return methods;
            });

            // Caching Fields
            final Map<String, Field> nbtTagCompoundClassFields = Reflections.cacheFields(nbtTagCompoundClass, aClass ->
                Collections.singletonList(aClass.getDeclaredField("map")));
            final Map<String, Field> nbtTagListClassFields = Reflections.cacheFields(nbtTagListClass, aClass ->
                Collections.singletonList(aClass.getDeclaredField("list")));

            // Caching Constructors
            @Nullable final Constructor<?> itemStackClassConstructor;
            if (Reflections.LOCAL_VERSION == MinecraftVersion.v1_11 ||
                Reflections.LOCAL_VERSION == MinecraftVersion.v1_12) {
                itemStackClassConstructor = itemStackClass.getConstructor(nbtTagCompoundClass);
            } else {
                itemStackClassConstructor = null;
            }

            // Caching References
            nbtTagListClassFields.forEach((s, field) -> field.setAccessible(true));
            nbtTagCompoundClassFields.forEach((s, field) -> field.setAccessible(true));
            craftMetaSkullClassMethods.forEach((s, method) -> method.setAccessible(true));

            Reflections.addReference(nbtBaseClass, null, nbtBaseClassMethods, new HashMap<>());
            Reflections.addReference(nbtTagCompoundClass, null, nbtTagCompoundClassMethods, nbtTagCompoundClassFields);
            Reflections.addReference(nbtTagListClass, null, new HashMap<>(), nbtTagListClassFields);
            Reflections.addReference(mojansonParserClass, null, mojansonParserClassMethods, new HashMap<>());
            Reflections.addReference(itemStackClass, itemStackClassConstructor, itemStackClassMethods, new HashMap<>());
            Reflections.addReference(craftItemStackClass, null, craftItemStackClassMethods, new HashMap<>());
            Reflections.addReference(entityClass, null, entityClassMethods, new HashMap<>());
            Reflections.addReference(craftEntityClass, null, craftEntityClassMethods, new HashMap<>());
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void addReference(@NotNull final Class<?> aClass,
                              @Nullable final Constructor<?> constructors,
                              @NotNull final Map<String, Method> methods,
                              @NotNull final Map<String, Field> fields) {
        Reflections.addReference(aClass.getSimpleName(), aClass, constructors, methods, fields);
    }

    @NotNull
    private void addReference(@NotNull final String key, @NotNull final Class<?> aClass,
                              @Nullable final Constructor<?> constructors,
                              @NotNull final Map<String, Method> methods,
                              @NotNull final Map<String, Field> fields) {
        Reflections.REF.put(key, new Reflections.Reference(key, aClass, constructors, methods, fields));
    }

    @NotNull
    private Class<?> findNMSClass(@NotNull final String classPath) throws ClassNotFoundException {
        return Reflections.findClass(Reflections.nmspath + classPath);
    }

    @NotNull
    private Class<?> findCBClass(@NotNull final String classPath) throws ClassNotFoundException {
        return Reflections.findClass(Reflections.cbpath + classPath);
    }

    @NotNull
    private Class<?> findAuthClass(@NotNull final String classPath) throws ClassNotFoundException {
        return Reflections.findClass(Reflections.authpath + classPath);
    }

    @NotNull
    private Class<?> findClass(@NotNull final String classPath) throws ClassNotFoundException {
        return Class.forName(classPath);
    }

    @NotNull
    private Map<String, Method> cacheMethods(@NotNull final Class<?> classKey,
                                             @NotNull final ThrowableFunction<Class<?>, Map<String, Method>> func)
        throws Exception {
        return func.apply(classKey);
    }

    @NotNull
    private Map<String, Field> cacheFields(@NotNull final Class<?> classKey,
                                           @NotNull final ThrowableFunction<Class<?>, List<Field>> func) throws Exception {
        return func.apply(classKey).stream()
            .collect(Collectors.toMap(Field::getName, field -> field));
    }

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

    }

}
