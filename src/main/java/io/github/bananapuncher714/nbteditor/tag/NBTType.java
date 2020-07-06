package io.github.bananapuncher714.nbteditor.tag;

import java.util.Arrays;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
@Getter
public enum NBTType {

    END(0),
    BYTE(1),
    SHORT(2),
    INTEGER(3),
    LONG(4),
    FLOAT(5),
    DOUBLE(6),
    BYTE_ARRAY(7),
    STRING(8),
    TAG_LIST(9),
    COMPOUND(10),
    INT_ARRAY(11),
    LONG_ARRAY(12);

    private final int id;

    @NotNull
    public static NBTType fromId(final int id) {
        return Arrays.stream(NBTType.values())
            .filter(nbtType -> nbtType.id == id)
            .findFirst()
            .orElse(NBTType.END);
    }

}