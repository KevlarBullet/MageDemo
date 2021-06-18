package gg.slvr.magedemo.listener;

import gg.slvr.magedemo.ItemUtils;
import gg.slvr.magedemo.MageDemo;
import gg.slvr.magedemo.SpellBook;
import gg.slvr.magedemo.spell.Spell;
import gg.slvr.magedemo.spell.Spells;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
//        Bukkit.getLogger().info(event.getInventory().getName());
        if (event.getInventory().getName().equals("Spell Book")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            ItemStack clickedItem = event.getCurrentItem();

            if (clickedItem != null) {
                String tag = ItemUtils.getTagString(clickedItem, "uid");
//                String tag2 = ItemUtils.getTagString(clickedItem, "pp");
//                player.sendMessage(tag2);

                if (tag != null) {
//                    player.sendMessage("pls");
                    char uid = tag.charAt(0);
                    Spell spell = Spells.getById(uid);
                    SpellBook book = MageDemo.getInstance().getPlayerInfo(player.getName()).getSpellBook();

                    book.onSelect(spell);
                    player.sendMessage(ChatColor.LIGHT_PURPLE + "Selected spell: " + spell.getName());

                    // TODO: Update item lore to say "SELECTED" or some crap
                } else {
                    player.sendMessage("a");
                }
            }
        }
    }

    // Should never happen anyway
    @EventHandler
    public void onDrag(InventoryDragEvent event) {
        if (event.getInventory().getName().equals("Spell Book")) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onItemDrop(PlayerDropItemEvent event) {
        Player player = event.getPlayer();
        InventoryView view = player.getOpenInventory();

        if (view.getTopInventory().getName().equals("Spell Book")) {
            event.setCancelled(true);
        }
    }

}
