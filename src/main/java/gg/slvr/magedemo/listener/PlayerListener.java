package gg.slvr.magedemo.listener;

import gg.slvr.magedemo.ItemUtils;
import gg.slvr.magedemo.MageDemo;
import gg.slvr.magedemo.SpellBook;
import gg.slvr.magedemo.spell.Spells;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class PlayerListener implements Listener {

    private final MageDemo instance = MageDemo.getInstance();

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getHand() == EquipmentSlot.HAND) {
            Player player = event.getPlayer();
            ItemStack stack = event.getItem();

            if (stack != null && stack.getType() == Material.STICK
                    && stack.hasItemMeta() && stack.getItemMeta().getDisplayName().equalsIgnoreCase("wand")) {
                if (player.isSneaking()) {
                    SpellBook book = MageDemo.getInstance().getPlayerInfo(player.getName()).getSpellBook();

                    if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                        book.openGUI();
                    } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        Block block = event.getClickedBlock();
                        Material matt = block.getType();

                        switch (matt) {
                            case MAGMA:
                                player.sendMessage(ChatColor.YELLOW + "You're a wizard, Harry.");
                                book.learnSpell(Spells.FIRE);
                                break;
                            case PACKED_ICE:
                                player.sendMessage(ChatColor.YELLOW + "You're a hizard, Warry.");
                                book.learnSpell(Spells.ICE);
                                break;
                            case BRICK:
                                player.sendMessage(ChatColor.YELLOW + "You're a hairy wizard.");
                                book.learnSpell(Spells.BRICK);
                                break;
                            default:
                                player.sendMessage(ChatColor.RED + "Nothing could be learned from this block");
                        }
                    }
                } else {
                    if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                        String tag = ItemUtils.getTagString(stack, "uid");

                        if (tag != null && !tag.equals("")) {
                            char uid = tag.charAt(0);
                            Spells.getById(uid).cast(player);
                        }
                    }
                }

            }

        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        instance.getPlayerInfo(event.getPlayer().getName(), true);
    }

}
