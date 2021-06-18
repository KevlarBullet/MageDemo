package gg.slvr.magedemo.spell;

import gg.slvr.magedemo.ItemUtils;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Player;

import java.util.Random;

public class Spellh extends Spell {

    public Spellh(String name, char id, Material material) {
        super(name, id, material);
    }

    @Override
    public void cast(Player caster) {
        Random random = new Random(System.currentTimeMillis());

        World world = ((CraftWorld) caster.getWorld()).getHandle();
        Location eyes = caster.getEyeLocation();

        IBlockData blockData = Blocks.PACKED_ICE.getBlockData();

        double x = eyes.getX();
        double y = eyes.getY();
        double z = eyes.getZ();

        float yaw = eyes.getYaw();
        float pitch = eyes.getPitch();

        world.a(null, x, y, z, SoundEffects.ch, SoundCategory.PLAYERS, 0.8f, 1.2f);

        for (int i = 0; i < 7; i++) {
            float r1 = random.nextFloat() * 45 - 22;
            float r2 = random.nextFloat() * 45 - 22;
            Vec3D dir = ItemUtils.fromPitchYaw(pitch + r1, yaw + r2).a(0.8D);

            EntityFallingBlock block = new EntityFallingBlock(world, x, y, z, blockData);

            block.ticksLived = -6000;
            block.dropItem = false;
            block.motX = dir.x;
            block.motY = dir.y + 0.1;
            block.motZ = dir.z;

            world.addEntity(block);
        }

    }

}
