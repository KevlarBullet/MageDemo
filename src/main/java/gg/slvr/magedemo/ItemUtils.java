package gg.slvr.magedemo;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import net.minecraft.server.v1_12_R1.NBTTagString;
import net.minecraft.server.v1_12_R1.Vec3D;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.inventory.ItemStack;

public class ItemUtils {

    // TODO: Lul @ the only method in the entire project with documentation is the one that's unimplemented (fix this)
    /**
     * Sets the given item stack's lore (inventory should be updated afterwards via player#updateInventory())
     * @param stack The item to update
     * @param line The specific lore line to set
     * @param description The new string that will be set in the given line
     */
    public static void setDescription(ItemStack stack, int line, String description) {
//        List<String> lore;
//
//        if (stack.getLore() != null) {
//            lore = stack.getLore();
//        } else {
//            lore = new ArrayList<>();
//        }
//
//        lore.set(line, description);
//        stack.setLore(lore);
    }

    public static ItemStack setTagString(ItemStack stack, String k, String v) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        nmsStack.a(k, new NBTTagString(v));

        return CraftItemStack.asCraftMirror(nmsStack);
    }

    public static String getTagString(ItemStack stack, String tag) {
        net.minecraft.server.v1_12_R1.ItemStack nmsStack = CraftItemStack.asNMSCopy(stack);
        NBTTagCompound tagCompound = nmsStack.getTag();

        if (tagCompound != null && tagCompound.getString(tag) != null) {
            return tagCompound.getString(tag);
        }

        return null;
    }

    // Not an item util but I don't feel like making another utility class for one method
    // (you could kinda just repurpose this as a general utility class, but you know...)
    // Stolen from Minecraft source
    public static Vec3D fromPitchYaw(float pitch, float yaw) {
        double f = Math.cos(-yaw * 0.017453292 - (float)Math.PI);
        double f1 = Math.sin(-yaw * 0.017453292 - (float)Math.PI);
        double f2 = -Math.cos(-pitch * 0.017453292);
        double f3 = Math.sin(-pitch * 0.017453292);

        return new Vec3D((f1 * f2), f3, (f * f2));
    }

}
