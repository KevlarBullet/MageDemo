package gg.slvr.magedemo.spell;

import gg.slvr.magedemo.ItemUtils;
import gg.slvr.magedemo.MageDemo;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

// Realistically these should be singletons
public class Smell extends Spell {

    public Smell(String name, char id, Material material) {
        super(name, id, material);
    }

    @Override
    public void cast(Player caster) {
        World world = caster.getWorld();
        net.minecraft.server.v1_12_R1.World nmsWorld = ((CraftWorld) world).getHandle();
        float yaw = caster.getEyeLocation().getYaw();
        float pitch = caster.getEyeLocation().getPitch();

        FireballTask fireballTask = new FireballTask(caster, nmsWorld, yaw, pitch);
        fireballTask.runTaskTimer(MageDemo.getInstance(), 0L, 8L);
    }

    private static class FireballTask extends BukkitRunnable {

        private static final Random random = new Random(System.currentTimeMillis());

        private int i = 0;

        private final Player caster;
        private final net.minecraft.server.v1_12_R1.World world;
        private final float yaw;
        private final float pitch;

        public FireballTask(Player caster, net.minecraft.server.v1_12_R1.World world, float yaw, float pitch) {
            this.caster = caster;
            this.world = world;
            this.yaw = yaw;
            this.pitch = pitch;
        }

        @Override
        public void run() {
            if (i++ >= 2) {
                cancel();
            }

            // I have no idea why I used NMS entities instead of regular ones
            EntityFireball fireball = new EntitySmallFireball(world);
            Location eyes = caster.getEyeLocation();
            Vec3D ivy = new Vec3D(eyes.getX(), eyes.getY(), eyes.getZ());

            float r = random.nextFloat() * 30 - 15;
            float r2 = random.nextFloat() * 30 - 15;

            float fYaw = yaw + r;
            float fPitch = pitch + r2;
            Vec3D dir = ItemUtils.fromPitchYaw(fPitch, fYaw).a();
            Vec3D dirt = dir.a(0.4D);

//            ivy.e((dir.a(2D)));
//            caster.sendMessage((int)r + " " + (int)r2);
            dir = dir.a(0.8D);

//            caster.sendMessage("o: " + (int)yaw + "y " + (int)pitch + "p --- n: " + (int)fYaw + "y " + (int)fPitch);

            fireball.setLocation(ivy.x + dirt.x, ivy.y + dirt.y - 0.2, ivy.z + dirt.z, 0, 0);
//            fireball.setDirection(dir.x, dir.y, dir.z);
//            fireball.setDirection(0, 0, -0.8);

            // EntityFireball#setDirection adds a huge amount of randomness to the angle for some reason, making it impossible to accurately set a direction
            // Fireballs seem to visually disappear using this method instead of fireball#setDirection() (which doesn't make sense), but will still travel on their normal path as expected
            fireball.dirY = dir.y;
            fireball.dirX = dir.x;
            fireball.dirZ = dir.z;

//            caster.sendMessage("a");
            fireball.shooter = ((CraftPlayer) caster).getHandle();
//            caster.sendMessage("b");

            world.addEntity(fireball);
            world.a(null, fireball.locX, fireball.locY, fireball.locZ, SoundEffects.G, SoundCategory.PLAYERS, 1.0f, 1.0f);
        }

    }

}
