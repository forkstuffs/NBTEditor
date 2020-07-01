package io.github.bananapuncher714.nbteditor.builder;

import io.github.bananapuncher714.nbteditor.NBTEditorNew;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public final class ItemStackBuilder extends CompoundBuilder<ItemStack, ItemStackBuilder> {

    public ItemStackBuilder(@NotNull final ItemStack object) {
        super(object);
    }

    @NotNull
    @Override
    public ItemStackBuilder self() {
        return this;
    }

    @NotNull
    @Override
    public ItemStackBuilder createNew(@NotNull final ItemStack itemStack) {
        return NBTEditorNew.itemStackBuilder(itemStack);
    }

}