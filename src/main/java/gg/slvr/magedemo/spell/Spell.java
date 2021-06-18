package gg.slvr.magedemo.spell;

import gg.slvr.magedemo.ItemUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

public abstract class Spell {

    final String name;
    final char id;
    final ItemStack displayItem;

    // ADDS SUPPORT FOR MATERIALS THAT NEED MATERIAL DATA EVEN THOUGH I'M LITERALLY NEVER GONNA USE THAT
    // WHY ARE WE YELLING???
    @SuppressWarnings("unused")
    protected Spell(String name, char id, MaterialData displayMaterial) {
        this(name, id, displayMaterial.toItemStack(1));
    }

    public Spell(String name, char id, Material material) {
        this(name, id, new ItemStack(material));
    }

    // TODO: Enforce ID uniqueness?
    private Spell(String name, char id, ItemStack displayItem) {
        this.name = name;
        this.id = id;
        this.displayItem = ItemUtils.setTagString(displayItem, "uid", String.valueOf(id));

        displayItem.getItemMeta().setDisplayName(name);
    }

    public abstract void cast(Player caster);

    public String getName() {
        return name;
    }

    public char getId() {
        return id;
    }

    public ItemStack getDisplayItem() {
        // Return a copy of displayItem in case the displayed stack in the GUI gets modified somehow
//        return new ItemStack(this.displayItem);
        return displayItem;
    }

}
