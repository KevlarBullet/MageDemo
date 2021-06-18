package gg.slvr.magedemo.spell;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

// The real spell book
// Basically an enum
// Could be merged with Spell.class, probably. Would also make the singleton design easier
public class Spells {

    private static final Map<Character, Spell> SPELLS_BY_ID = new HashMap<>();

    public static final Spell BRICK = new Spellb("Brick", '#', Material.BRICK);
    public static final Spell ICE = new Spellh("Ice", '!', Material.PACKED_ICE);
    public static final Spell FIRE = new Smell("Fireball", '@', Material.MAGMA);

    static {
        SPELLS_BY_ID.put(BRICK.id, BRICK);
        SPELLS_BY_ID.put(ICE.id, ICE);
        SPELLS_BY_ID.put(FIRE.id, FIRE);
    }

    public static Spell getById(char id) {
        return SPELLS_BY_ID.get(id);
    }

}
