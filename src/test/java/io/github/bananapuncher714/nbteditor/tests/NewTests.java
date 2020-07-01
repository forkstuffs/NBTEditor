package io.github.bananapuncher714.nbteditor.tests;

import io.github.bananapuncher714.nbteditor.NBTEditorNew;
import lombok.experimental.UtilityClass;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

@UtilityClass
public class NewTests {

    public void test() {
        final ItemStack itemStack = new ItemStack(Material.DIAMOND);
        NBTEditorNew.ItemStackBuilder builder = NBTEditorNew.fromItemStack(itemStack);
        builder
            .setTagIfAbsent("object", "path", "to", "tag")
            .self();
    }

}
