package org.entityapi.nms.v1_7_R1.entity;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.api.EntitySound;
import org.entityapi.api.mind.Attribute;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.api.reflection.SafeField;
import org.entityapi.nms.v1_7_R1.entity.mind.attribute.RideAttribute;
import org.entityapi.nms.v1_7_R1.reflection.PathfinderGoalSelectorRef;

public class ControllableHorseEntity extends EntityHorse implements ControllableEntityHandle {

    private final ControllableEntity controllableEntity;

    public ControllableHorseEntity(World world, ControllableEntity controllableEntity) {
        super(world);
        this.controllableEntity = controllableEntity;
        new PathfinderGoalSelectorRef(this).clearGoals();
    }

    @Override
    public ControllableEntity getControllableEntity() {
        return this.controllableEntity;
    }

    // EntityInsentient - Most importantly stops NMS goal selectors from ticking
    @Override
    protected void bn() {
        ++this.aV;

        this.w();

        this.getEntitySenses().a();

        //this.targetSelector.a();
        //this.goalSelector.a();

        this.getNavigation().f();

        this.bp();

        this.getControllerMove().c();
        this.getControllerLook().a();
        this.getControllerJump().b();
    }

    @Override
    public void h() {
        super.h();
        if (this.controllableEntity != null) {
            EntityAPI.getCore().callOnTick(this.controllableEntity);
            if (this.controllableEntity.shouldUpdateAttributes()) {
                this.controllableEntity.getMind().tick();
            }
        }
    }

    @Override
    public void collide(Entity entity) {
        if (this.controllableEntity == null) {
            super.collide(entity);
            return;
        }

        if (EntityAPI.getCore().callOnCollide(this.controllableEntity, entity.getBukkitEntity())) {
            super.collide(entity);
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        if (this.controllableEntity == null || !(entity.getBukkitEntity() instanceof Player)) {
            return super.c(entity);
        }

        return EntityAPI.getCore().callOnInteract(this.controllableEntity, (Player) entity.getBukkitEntity(), true);
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float v) {
        if (this.controllableEntity != null && damageSource.getEntity() != null && damageSource.getEntity().getBukkitEntity() instanceof Player) {
            EntityAPI.getCore().callOnInteract(this.controllableEntity, (Player) damageSource.getEntity(), false);
        }
        return super.damageEntity(damageSource, v);
    }

    @Override
    public void e(float xMotion, float zMotion) {
        float[] motion = new float[]{xMotion, (float) this.motY, zMotion};
        if (this.controllableEntity != null) {
            if (this.controllableEntity.getMind().hasAttribute("RIDE")) {
                Attribute b = this.controllableEntity.getMind().getAttribute("RIDE");
                if (b instanceof RideAttribute) {
                    ((RideAttribute) b).onRide(motion);
                }
            }
        }
        this.motY = motion[1];
        super.e(motion[0], motion[2]);
    }

    @Override
    public void g(double x, double y, double z) {
        if (this.controllableEntity != null) {
            Vector velocity = EntityAPI.getCore().callOnPush(this.controllableEntity, x, y, z);
            x = velocity.getX();
            y = velocity.getY();
            z = velocity.getZ();
        }
        super.g(x, y, z);
    }

    @Override
    public void die(DamageSource damagesource) {
        if (this.controllableEntity != null) {
            EntityAPI.getCore().callOnDeath(this.controllableEntity);
        }
        super.die(damagesource);
    }

    @Override
    public org.bukkit.Material getDefaultMaterialLoot() {
        return CraftMagicNumbers.getMaterial(this.getLoot());
    }

    @Override
    protected Item getLoot() {
        org.bukkit.Material lootMaterial = this.controllableEntity.getLoot();
        return this.controllableEntity == null ? super.getLoot() : lootMaterial == null ? super.getLoot() : CraftMagicNumbers.getItem(lootMaterial);
    }

    @Override
    protected String t() {
        this.cQ();
        if (this.random.nextInt(10) == 0 && !this.bh()) {
            this.cS();
        }

        int i = this.getType();

        return i == 3 ? this.getSoundFor(EntitySound.IDLE, "mob.horse.zombie.idle", "zombie") : (i == 4 ? this.getSoundFor(EntitySound.IDLE, "mob.horse.skeleton.idle", "skeleton") : (i != 1 && i != 2 ? this.getSoundFor(EntitySound.IDLE, "mob.horse.idle", "normal") : this.getSoundFor(EntitySound.IDLE, "mob.horse.donkey.idle", "donkey")));
    }

    @Override
    protected String aT() {
        this.cQ();
        if (this.random.nextInt(3) == 0) {
            this.cS();
        }

        int i = this.getType();

        return i == 3 ? this.getSoundFor(EntitySound.HURT, "mob.horse.zombie.hit", "zombie") : (i == 4 ? this.getSoundFor(EntitySound.HURT, "mob.horse.skeleton.hit", "skeleton") : (i != 1 && i != 2 ? this.getSoundFor(EntitySound.HURT, "mob.horse.hit", "normal") : this.getSoundFor(EntitySound.HURT, "mob.horse.donkey.hit", "donkey")));
    }

    @Override
    protected String aU() {
        this.cQ();
        int i = this.getType();

        return i == 3 ? this.getSoundFor(EntitySound.DEATH, "mob.horse.zombie.death", "zombie") : (i == 4 ? this.getSoundFor(EntitySound.DEATH, "mob.horse.skeleton.death", "skeleton") : (i != 1 && i != 2 ? this.getSoundFor(EntitySound.DEATH, "mob.horse.death", "normal") : this.getSoundFor(EntitySound.DEATH, "mob.horse.donkey.death", "donkey")));
    }

    // Taken from EntityHorse and modified to suit custom sounds
    @Override
    protected void a(int i, int j, int k, Block block) {
        StepSound stepsound = block.stepSound;

        if (this.world.getType(i, j + 1, k) == Blocks.SNOW) {
            stepsound = Blocks.SNOW.stepSound;
        }

        if (!block.getMaterial().isLiquid()) {
            int l = this.getType();

            if (this.passenger != null && l != 1 && l != 2) {
                //++this.bP;
                SafeField<Integer> field_bP = new SafeField<>(EntityHorse.class, "bP");
                field_bP.set(this, field_bP.get(this) + 1);

                int bP = field_bP.get(this);

                //if (this.bP > 5 && this.bP % 3 == 0) {
                if (bP > 5 && bP % 3 == 0) {
                    this.makeSound(this.getSoundFor(EntitySound.STEP, "mob.horse.gallop", "withpassenger"), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
                    if (l == 0 && this.random.nextInt(10) == 0) {
                        this.makeSound(this.getSoundFor(EntitySound.BREATHE, "mob.horse.breathe"), stepsound.getVolume1() * 0.6F, stepsound.getVolume2());
                    }
                    //} else if (this.bP <= 5) {
                } else if (bP <= 5) {
                    this.makeSound(this.getSoundFor(EntitySound.STEP, "mob.horse.wood", "wood"), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
                }
            } else if (stepsound == Block.f) {
                this.makeSound(this.getSoundFor(EntitySound.STEP, "mob.horse.wood", "wood"), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
            } else {
                this.makeSound(this.getSoundFor(EntitySound.STEP, "mob.horse.soft", "soft"), stepsound.getVolume1() * 0.15F, stepsound.getVolume2());
            }
        }
    }

    // Taken from EntityHorse and modified to suit custom sounds
    @Override
    public void a(InventorySubcontainer inventorysubcontainer) {
        int i = this.cj();
        boolean flag = this.cs();

        this.cM();
        if (this.ticksLived > 20) {
            if (i == 0 && i != this.cj()) {
                this.makeSound(this.getSoundFor(EntitySound.STEP, "mob.horse.armor", "armor"), 0.5F, 1.0F);
            } else if (i != this.cj()) {
                this.makeSound(this.getSoundFor(EntitySound.STEP, "mob.horse.armor", "armor"), 0.5F, 1.0F);
            }

            if (!flag && this.cs()) {
                this.makeSound(this.getSoundFor(EntitySound.STEP, "mob.horse.leather", "leatherarmor"), 0.5F, 1.0F);
            }
        }
    }

    // Other stuff
    // animations, sounds, etc.

    private String getSoundFor(EntitySound soundType, String def) {
        return this.controllableEntity == null ? def : this.controllableEntity.getSound(soundType);
    }

    private String getSoundFor(EntitySound soundType, String def, String key) {
        return this.controllableEntity == null ? def : this.controllableEntity.getSound(soundType, key);
    }

    // Catch any other sounds that are deep in NMS methods
    @Override
    public void makeSound(String s, float f, float f1) {
        if (s.equals("mob.horse.jump")) {
            s = this.getSoundFor(EntitySound.JUMP, "mob.horse.jump");
        } else if (s.equals("mob.horse.land")) {
            this.getSoundFor(EntitySound.STEP, "mob.horse.land", "land");
        }
        super.makeSound(s, f, f1);
    }

    private void cQ() {
        if (!this.world.isStatic) {
            SafeField<Integer> field_bE = new SafeField<>(EntityHorse.class, "bE");
            field_bE.set(this, 1);

            // Open the horse's mouth (animation 128)
            this.horseVisual(128, true);
        }
    }

    private void cS() {
        if (!this.world.isStatic) {
            SafeField<Integer> field_bF = new SafeField<>(EntityHorse.class, "bF");
            field_bF.set(this, 1);

            // Stop looking down (animation 32)
            this.p(true);
        }
    }

    private void cM() {
        if (!this.world.isStatic) {
            this.n(this.inventoryChest.getItem(0) != null);
            if (this.cz()) {
                this.d(this.inventoryChest.getItem(1));
            }
        }
    }

    /*
     * Valid horse visuals:
     * 4 = saddle
     * 8 = chest
     * 32 = head down
     * 64 = rear
     * 128 = mouth open
     */
    private void horseVisual(int i, boolean flag) {
        int j = this.datawatcher.getInt(16);

        if (flag) {
            this.datawatcher.watch(16, Integer.valueOf(j | i));
        } else {
            this.datawatcher.watch(16, Integer.valueOf(j & ~i));
        }
    }
}