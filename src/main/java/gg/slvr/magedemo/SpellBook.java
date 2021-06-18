package gg.slvr.magedemo;

import gg.slvr.magedemo.spell.Spell;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class SpellBook {

    private Player player;
    private final Map<Character, Spell> discoveredSpells = new HashMap<>();

    public SpellBook(Player player) {
        this.player = player;
    }

    public void openGUI() {
        Inventory inventory = Bukkit.getServer().createInventory(null, 54, "Spell Book");

        for (Spell spell : discoveredSpells.values()) {
            ItemStack spellStack = spell.getDisplayItem();
            ItemMeta meta = spellStack.getItemMeta();

            meta.setDisplayName(spell.getName());
            spellStack.setItemMeta(meta);

            inventory.addItem(spellStack);
        }

        player.openInventory(inventory);
    }

    public void learnSpell(Spell spell) {
        if (!discoveredSpells.containsKey(spell.getId())) {
            discoveredSpells.put(spell.getId(), spell);
            player.sendMessage(ChatColor.YELLOW + "You leaned a spell!  :D    " + spell.getName());
        }
    }

    public void onSelect(Spell spell) {
        ItemStack stack = player.getInventory().getItemInMainHand();

        if (stack != null && stack.getType() == Material.STICK
                && stack.hasItemMeta() && stack.getItemMeta().getDisplayName().equalsIgnoreCase("wand")) {
            ItemUtils.setDescription(stack, 0, ChatColor.RED + "Selected spell: " + spell.getName());

            player.getInventory().setItemInMainHand(ItemUtils.setTagString(stack, "uid", String.valueOf(spell.getId())));
        }
    }

    public void updatePlayer(Player player) {
        this.player = player;
    }

}
