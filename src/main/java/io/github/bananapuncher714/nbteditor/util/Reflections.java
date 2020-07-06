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
public class Reflections {

    @NotNull
    public final MinecraftVersion LOCAL_VERSION;

    @NotNull
    public final String VERSION;

    private final String nmspath = "net.minecraft.server." + Reflections.VERSION + '.';

    private final String cbpath = "org.bukkit.craftbukkit." + Reflections.VERSION + '.';

    private final String authpath = "com.mojang.authlib.";

    private final Map<String, Reference> REF = new HashMap<>();

    static {
        VERSION = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
        LOCAL_VERSION = Objects.requireNonNull(MinecraftVersion.get(Reflections.VERSION));
        try {
            // Caching NMS|CB|MOJANG Classes
            final Class<?> mojansonParserClass = Reflections.findNMSClass("MojangsonParser");
            final Class<?> itemStackClass = Reflections.findNMSClass("ItemStack");
            final Class<?> craftItemStackClass = Reflections.findCBClass("inventory.CraftItemStack");
            final Class<?> craftMetaSkullClass = Reflections.findCBClass("inventory.CraftMetaSkull");
            final Class<?> entityClass = Reflections.findNMSClass("Entity");
            final Class<?> craftEntityClass = Reflections.findCBClass("entity.CraftEntity");
            final Class<?> craftWorldClass = Reflections.findCBClass("CraftWorld");
            final Class<?> craftBlockStateClass = Reflections.findCBClass("block." + "CraftBlockState");
            final Class<?> blockPositionClass = Reflections.findNMSClass("BlockPosition");
            final Class<?> tileEntityClass = Reflections.findNMSClass("TileEntity");
            final Class<?> worldClass = Reflections.findNMSClass("World");
            final Class<?> iBlockDataClass = Reflections.findNMSClass("IBlockData");
            final Class<?> tileEntitySkullClass = Reflections.findNMSClass("TileEntitySkull");
            final Class<?> gameProfileClass = Reflections.findAuthClass("GameProfile");
            final Class<?> propertyClass = Reflections.findAuthClass("properties.Property");
            final Class<?> propertyMapClass = Reflections.findAuthClass("properties.PropertyMap");

            // Caching NBT Classes
            final Class<?> nbtBaseClass = Reflections.findNMSClass("NBTBase");
            final Class<?> nbtNumber = Reflections.findNMSClass("NBTNumber");
            final Class<?> nbtTagCompoundClass = Reflections.findNMSClass("NBTTagCompound");
            final Class<?> nbtTagListClass = Reflections.findNMSClass("NBTTagList");
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
            final Map<String, Method> nbtBaseClassMethods = Collections.singletonMap("getTypeId", nbtBaseClass.getMethod("getTypeId"));
            final Map<String, Method> nbtTagCompoundClassMethods = new HashMap<>();
            nbtTagCompoundClassMethods.put("get", nbtTagCompoundClass.getMethod("get", String.class));
            nbtTagCompoundClassMethods.put("set", nbtTagCompoundClass.getMethod("set", String.class, nbtBaseClass));
            nbtTagCompoundClassMethods.put("hasKey", nbtTagCompoundClass.getMethod("hasKey", String.class));
            nbtTagCompoundClassMethods.put("remove", nbtTagCompoundClass.getMethod("remove", String.class));
            if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                nbtTagCompoundClassMethods.put("getKeys", nbtTagCompoundClass.getMethod("getKeys"));
            } else {
                nbtTagCompoundClassMethods.put("getKeys", nbtTagCompoundClass.getMethod("c"));
            }
            final Map<String, Method> mojansonParserClassMethods = Collections.singletonMap("parse", mojansonParserClass.getMethod("parse", String.class));
            final Map<String, Method> itemStackClassMethods = new HashMap<>();
            itemStackClassMethods.put("hasTag", itemStackClass.getMethod("hasTag"));
            itemStackClassMethods.put("getTag", itemStackClass.getMethod("getTag"));
            itemStackClassMethods.put("setTag", itemStackClass.getMethod("setTag", nbtTagCompoundClass));
            itemStackClassMethods.put("save", itemStackClass.getMethod("save", nbtTagCompoundClass));
            if (Reflections.LOCAL_VERSION.lessThanOrEqualTo(MinecraftVersion.v1_10)) {
                itemStackClassMethods.put("createStack", itemStackClass.getMethod("createStack", nbtTagCompoundClass));
            } else if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_13)) {
                itemStackClassMethods.put("createStack", itemStackClass.getMethod("a", nbtTagCompoundClass));
            }
            final Map<String, Method> craftItemStackClassMethods = new HashMap<>();
            craftItemStackClassMethods.put("asNMSCopy", craftItemStackClass.getMethod("asNMSCopy", ItemStack.class));
            craftItemStackClassMethods.put("asBukkitCopy", craftItemStackClass.getMethod("asBukkitCopy", itemStackClass));
            final Map<String, Method> craftMetaSkullClassMethods = new HashMap<>();
            try {
                craftMetaSkullClassMethods.putAll(Collections.singletonMap("setProfile", craftMetaSkullClass.getDeclaredMethod("setProfile", gameProfileClass)));
            } catch (final NoSuchMethodException ignored) {
                // The method doesn't exist, so it's before 1.15.2
            }
            final Map<String, Method> entityClassMethods = new HashMap<>();
            if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                entityClassMethods.put("save", entityClass.getMethod("save", nbtTagCompoundClass));
                entityClassMethods.put("load", entityClass.getMethod("load", nbtTagCompoundClass));
            } else {
                entityClassMethods.put("save", entityClass.getMethod("c", nbtTagCompoundClass));
                entityClassMethods.put("load", entityClass.getMethod("f", nbtTagCompoundClass));
            }
            final Map<String, Method> craftEntityClassMethods = Collections.singletonMap("getHandle", craftEntityClass.getMethod("getHandle"));
            final Map<String, Method> tileEntityClassMethods = new HashMap<>();
            if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                tileEntityClassMethods.put("load", tileEntityClass.getMethod("load", iBlockDataClass, nbtTagCompoundClass));
            } else if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_12)) {
                tileEntityClassMethods.put("load", tileEntityClass.getMethod("load", nbtTagCompoundClass));
            } else {
                tileEntityClassMethods.put("load", tileEntityClass.getMethod("a", nbtTagCompoundClass));
            }
            try {
                tileEntityClassMethods.put("save", tileEntityClass.getMethod("save", nbtTagCompoundClass));
            } catch (final NoSuchMethodException exception) {
                try {
                    tileEntityClassMethods.put("save", tileEntityClass.getMethod("b", nbtTagCompoundClass));
                } catch (final NoSuchMethodException e) {
                    e.printStackTrace();
                }
            } catch (final SecurityException e) {
                e.printStackTrace();
            }
            final Map<String, Method> worldClassMethods = new HashMap<>();
            if (Reflections.LOCAL_VERSION.greaterThanOrEqualTo(MinecraftVersion.v1_16)) {
                worldClassMethods.put("getType", worldClass.getMethod("getType", blockPositionClass));
            }
            worldClassMethods.put("getTileEntity", worldClass.getMethod("getTileEntity", blockPositionClass));
            final Map<String, Method> craftWorldClassMethods = Collections.singletonMap("getWorldHandle", craftWorldClass.getMethod("getHandle"));
            final Map<String, Method> tileEntitySkullClassMethods = Collections.singletonMap("setGameProfile", tileEntitySkullClass.getMethod("setGameProfile", gameProfileClass));
            final Map<String, Method> gameProfileClassMethods = Collections.singletonMap("getProperties", gameProfileClass.getMethod("getProperties"));
            final Map<String, Method> propertyClassMethods = new HashMap<>();
            propertyClassMethods.put("getName", propertyClass.getMethod("getName"));
            propertyClassMethods.put("getValue", propertyClass.getMethod("getValue"));
            final Map<String, Method> propertyMapClassMethods = new HashMap<>();
            propertyMapClassMethods.put("values", propertyMapClass.getMethod("values"));
            propertyMapClassMethods.put("put", propertyMapClass.getMethod("put", Object.class, Object.class));

            // Caching Constructors
            @Nullable final Constructor<?> itemStackClassConstructor;
            if (Reflections.LOCAL_VERSION == MinecraftVersion.v1_11 ||
                Reflections.LOCAL_VERSION == MinecraftVersion.v1_12) {
                itemStackClassConstructor = itemStackClass.getConstructor(nbtTagCompoundClass);
            } else {
                itemStackClassConstructor = null;
            }
            final Constructor<?> gameProfileClassConstructor = gameProfileClass.getConstructor(UUID.class, String.class);
            final Constructor<?> propertyClassConstructor = propertyClass.getConstructor(String.class, String.class);
            final Constructor<?> blockPositionClassConstructor = blockPositionClass.getConstructor(int.class, int.class, int.class);
            final Constructor<?> nbtTagByteClassConstructor = nbtTagByteClass.getDeclaredConstructor(byte.class);
            final Constructor<?> nbtTagStringClassConstructor = nbtTagStringClass.getDeclaredConstructor(String.class);
            final Constructor<?> nbtTagDoubleClassConstructor = nbtTagDoubleClass.getDeclaredConstructor(double.class);
            final Constructor<?> nbtTagIntClassConstructor = nbtTagIntClass.getDeclaredConstructor(int.class);
            final Constructor<?> nbtTagLongClassConstructor = nbtTagLongClass.getDeclaredConstructor(long.class);
            final Constructor<?> nbtTagFloatClassConstructor = nbtTagFloatClass.getDeclaredConstructor(float.class);
            final Constructor<?> nbtTagShortClassConstructor = nbtTagShortClass.getDeclaredConstructor(short.class);
            final Constructor<?> nbtTagByteArrayClassConstructor = nbtTagByteArrayClass.getDeclaredConstructor(byteArrayClass);
            final Constructor<?> nbtTagIntArrayClassConstructor = nbtTagIntArrayClass.getDeclaredConstructor(intArrayClass);
            final Map<Class<?>, Constructor<?>> nbtMap = new HashMap<>();
            nbtMap.put(nbtTagByteClass, nbtTagByteClassConstructor);
            nbtMap.put(nbtTagStringClass, nbtTagStringClassConstructor);
            nbtMap.put(nbtTagDoubleClass, nbtTagDoubleClassConstructor);
            nbtMap.put(nbtTagIntClass, nbtTagIntClassConstructor);
            nbtMap.put(nbtTagLongClass, nbtTagLongClassConstructor);
            nbtMap.put(nbtTagShortClass, nbtTagShortClassConstructor);
            nbtMap.put(nbtTagFloatClass, nbtTagFloatClassConstructor);
            nbtMap.put(nbtTagByteArrayClass, nbtTagByteArrayClassConstructor);
            nbtMap.put(nbtTagByteArrayClass, nbtTagByteArrayClassConstructor);
            nbtMap.put(nbtTagIntArrayClass, nbtTagIntArrayClassConstructor);
            nbtMap.put(nbtTagByteArrayClass, nbtTagByteArrayClassConstructor);
            for (final Map.Entry<Class<?>, Constructor<?>> entry : nbtMap.entrySet()) {
                final Class<?> key = entry.getKey();
                final Field data = key.getDeclaredField("data");
                data.setAccessible(true);
                Reflections.addReference(key, entry.getValue(), new HashMap<>(), Collections.singletonMap("data", data));
            }

            // Caching Fields
            final Map<String, Field> nbtTagCompoundClassFields = Collections.singletonMap("map", nbtTagCompoundClass.getDeclaredField("map"));
            final Map<String, Field> nbtTagListClassFields = Collections.singletonMap("list", nbtTagListClass.getDeclaredField("list"));

            // Caching References
            nbtTagListClassFields.forEach((s, field) -> field.setAccessible(true));
            nbtTagCompoundClassFields.forEach((s, field) -> field.setAccessible(true));
            craftMetaSkullClassMethods.forEach((s, method) -> method.setAccessible(true));
            Arrays.asList(nbtTagByteClassConstructor,
                nbtTagStringClassConstructor, nbtTagDoubleClassConstructor, nbtTagIntClassConstructor,
                nbtTagIntClassConstructor, nbtTagLongClassConstructor, nbtTagFloatClassConstructor,
                nbtTagShortClassConstructor, nbtTagByteArrayClassConstructor, nbtTagIntArrayClassConstructor)
                .forEach(cons -> cons.setAccessible(true));

            Reflections.addReference(nbtBaseClass, nbtBaseClassMethods);
            Reflections.addReference(nbtTagCompoundClass, nbtTagCompoundClassMethods, nbtTagCompoundClassFields);
            Reflections.addReference(nbtTagListClass, new HashMap<>(), nbtTagListClassFields);
            Reflections.addReference(mojansonParserClass, mojansonParserClassMethods);
            Reflections.addReference(itemStackClass, itemStackClassConstructor, itemStackClassMethods);
            Reflections.addReference(craftItemStackClass, craftItemStackClassMethods);
            Reflections.addReference(entityClass, entityClassMethods);
            Reflections.addReference(craftEntityClass, craftEntityClassMethods);
            Reflections.addReference(tileEntityClass, tileEntityClassMethods);
            Reflections.addReference(worldClass, worldClassMethods);
            Reflections.addReference(craftWorldClass, craftWorldClassMethods);
            Reflections.addReference(tileEntitySkullClass, tileEntitySkullClassMethods);
            Reflections.addReference(gameProfileClass, gameProfileClassConstructor, gameProfileClassMethods);
            Reflections.addReference(propertyClass, propertyClassConstructor, propertyClassMethods);
            Reflections.addReference(propertyMapClass, propertyMapClassMethods);
            Reflections.addReference(blockPositionClass, blockPositionClassConstructor, new HashMap<>());
            Reflections.addReference(craftBlockStateClass);
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    public Optional<Constructor<?>> findConstructor(@NotNull final String key) {
        return Reflections.findReference(key).getConstructor();
    }

    @NotNull
    public Optional<Method> findMethod(@NotNull final String key, @NotNull final String name) {
        return Reflections.findReference(key).getMethod(name);
    }

    @NotNull
    public Optional<Field> findField(@NotNull final String key, @NotNull final String name) {
        return Reflections.findReference(key).getField(name);
    }

    @NotNull
    public Reference findReference(@NotNull final String key) {
        return Optional.ofNullable(Reflections.REF.get(key)).orElseThrow(() ->
            new RuntimeException("Reference called + " + key + " not found!"));
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

    private void addReference(@NotNull final Class<?> aClass) {
        Reflections.addReference(aClass.getSimpleName(), aClass, null, new HashMap<>(), new HashMap<>());
    }

    private void addReference(@NotNull final Class<?> aClass, @Nullable final Constructor<?> constructors) {
        Reflections.addReference(aClass.getSimpleName(), aClass, constructors, new HashMap<>(), new HashMap<>());
    }

    private void addReference(@NotNull final Class<?> aClass, @NotNull final Map<String, Method> methods) {
        Reflections.addReference(aClass.getSimpleName(), aClass, null, methods, new HashMap<>());
    }

    private void addReference(@NotNull final Class<?> aClass, @Nullable final Constructor<?> constructors,
                              @NotNull final Map<String, Method> methods) {
        Reflections.addReference(aClass.getSimpleName(), aClass, constructors, methods, new HashMap<>());
    }

    private void addReference(@NotNull final Class<?> aClass, @NotNull final Map<String, Method> methods,
                              @NotNull final Map<String, Field> fields) {
        Reflections.addReference(aClass.getSimpleName(), aClass, null, methods, fields);
    }

    private void addReference(@NotNull final Class<?> aClass, @Nullable final Constructor<?> constructors,
                              @NotNull final Map<String, Method> methods,
                              @NotNull final Map<String, Field> fields) {
        Reflections.addReference(aClass.getSimpleName(), aClass, constructors, methods, fields);
    }

    @NotNull
    private void addReference(@NotNull final String key, @NotNull final Class<?> aClass,
                              @Nullable final Constructor<?> constructors, @NotNull final Map<String, Method> methods,
                              @NotNull final Map<String, Field> fields) {
        Reflections.REF.put(key, new Reference(key, aClass, constructors, methods, fields));
    }

}
