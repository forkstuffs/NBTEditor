package io.github.bananapuncher714.nbteditor.tag.abs;

public interface NBTNumber extends NBTBase {

    default long longValue() {
        return 0L;
    }

    default int intValue() {
        return 0;
    }

    default short shortValue() {
        return (short) 0;
    }

    default byte byteValue() {
        return (byte) 0;
    }

    default double doubleValue() {
        return 0.0d;
    }

    default float floatValue() {
        return 0.0f;
    }

}
