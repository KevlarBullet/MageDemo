package gg.slvr.magedemo.spell;

import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.List;

public class Spellb extends Spell {

    public Spellb(String name, char id, Material material) {
        super(name, id, material);
    }

    @Override
    public void cast(Player caster) {
        org.bukkit.World world = caster.getWorld();
        net.minecraft.server.v1_12_R1.World nmsWorld = ((CraftWorld) world).getHandle();
        Location loc = caster.getLocation();

        // It's so much easier when it doesn't have to be randomized
        Vector dir = caster.getEyeLocation().getDirection().add(new Vector(0, 0.20, 0)).multiply(1.5);

        EntityBrick thrownBrick = new EntityBrick(nmsWorld, loc.getX(), loc.getY() + 1.1, loc.getZ(), 0.3, caster);
        thrownBrick.motX = dir.getX();
        thrownBrick.motY = dir.getY();
        thrownBrick.motZ = dir.getZ();

        nmsWorld.addEntity(thrownBrick);
        nmsWorld.a(null, thrownBrick.locX, thrownBrick.locY, thrownBrick.locZ, SoundEffects.hp, SoundCategory.PLAYERS, 1.0f, 0.5f);

//        caster.sendMessage(ChatColor.AQUA + "This isn't implemented yet, so just imagine a brick flying away from your face");
    }

    private static class EntityBrick extends EntityItem {

        private static final ItemStack BRICK = new ItemStack(CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.BRICK)).getItem(), 1);

        private final double width;
        private final EntityHuman caster;

        private int life = 0;

        public EntityBrick(World world, double x, double y, double z, double width, Player caster) {
            super(world, x, y, z, new ItemStack(CraftItemStack.asNMSCopy(new org.bukkit.inventory.ItemStack(Material.CLAY_BRICK)).getItem(), 1));

            this.width = width;
            this.caster = ((CraftPlayer) caster).getHandle();

            this.s();
        }

        @Override
        public void B_() {
            super.B_();

            // Why is age private REEEEEEEEEEEEEEEEEEE
            this.life++;

            if (!this.onGround) {
                AxisAlignedBB boundingBox = new AxisAlignedBB(this.locX - width, this.locY, this.locZ - width, this.locX + width, this.locY + width * 2, this.locZ + width);
                List<EntityLiving> hitEntities = this.world.a(EntityLiving.class, boundingBox);

                if (!hitEntities.isEmpty()) {
                    for (EntityLiving entityLiving : hitEntities) {
                        if (!entityLiving.equals(caster)) {
                            entityLiving.damageEntity(DamageSource.playerAttack(this.caster), 1);
                            this.die();
                        }
                    }

                }
            }

            if (this.life >= 61 && this.pickupDelay > 0) {
                this.a(0);
            }
        }

    }

}
