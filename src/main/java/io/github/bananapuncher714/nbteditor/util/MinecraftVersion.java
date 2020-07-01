package io.github.bananapuncher714.nbteditor.util;

import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Minecraft variables as enums
 *
 * @author BananaPuncher714
 */
@RequiredArgsConstructor
public enum MinecraftVersion {
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
    public static MinecraftVersion get(@NotNull final String text) {
        return Arrays.stream(MinecraftVersion.values())
            .filter(version -> text.contains(version.key))
            .findFirst()
            .orElse(null);
    }

    // Would be really cool if we could overload operators here
    public boolean greaterThanOrEqualTo(@NotNull final MinecraftVersion other) {
        return this.order >= other.order;
    }

    public boolean lessThanOrEqualTo(@NotNull final MinecraftVersion other) {
        return this.order <= other.order;
    }
}