package gg.slvr.magedemo;

import org.bukkit.entity.Player;

// TODO: cereal lmap
// This is honestly just a holder for spell books, and could probably be replaced with just that
public class PlayerInfo {

    // Honestly probably won't be used
    private Player player;

    private final SpellBook spellBook;

    public PlayerInfo(Player player) {
        this.player = player;
        this.spellBook = new SpellBook(player);
    }

    public void setPlayer(Player player) {
        this.player = player;
        this.spellBook.updatePlayer(player);
    }

    public SpellBook getSpellBook() {
        return spellBook;
    }

}
