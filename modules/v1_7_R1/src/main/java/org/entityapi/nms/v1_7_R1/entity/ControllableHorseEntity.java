/*
 * Copyright (C) EntityAPI Team
 *
 * This file is part of EntityAPI.
 *
 * EntityAPI is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * EntityAPI is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with EntityAPI.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.entityapi.nms.v1_7_R1.entity;

import com.captainbern.reflection.Reflection;
import com.captainbern.reflection.SafeField;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.attribute.*;
import org.entityapi.api.entity.type.ControllableHorse;
import org.entityapi.api.entity.type.nms.ControllableHorseHandle;
import org.entityapi.api.events.Action;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.api.utils.EntityUtil;

import static com.captainbern.reflection.matcher.Matchers.withExactType;

public class ControllableHorseEntity extends EntityHorse implements ControllableHorseHandle {

    private final ControllableHorse controllableEntity;

    public ControllableHorseEntity(World world, ControllableHorse controllableEntity) {
        super(world);
        this.controllableEntity = controllableEntity;
        EntityUtil.clearGoals(this);
    }

    @Override
    public ControllableHorse getControllableEntity() {
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
    public void move(double d0, double d1, double d2) {
        if (this.controllableEntity != null && this.controllableEntity.isStationary()) {
            return;
        }
        super.move(d0, d1, d2);
    }

    @Override
    public void h() {
        super.h();
        if (this.controllableEntity != null) {
            this.controllableEntity.getMind().getAttribute(TickAttribute.class).call(this.controllableEntity);
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

        if (!this.controllableEntity.getMind().getAttribute(CollideAttribute.class).call(this.controllableEntity, entity.getBukkitEntity()).isCancelled()) {
            super.collide(entity);
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        if (this.controllableEntity == null || !(entity.getBukkitEntity() instanceof Player)) {
            return super.c(entity);
        }

        return !this.controllableEntity.getMind().getAttribute(InteractAttribute.class).call(this.controllableEntity, entity.getBukkitEntity(), Action.RIGHT_CLICK).isCancelled();
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float v) {
        if (this.controllableEntity != null && damageSource.getEntity() != null && damageSource.getEntity().getBukkitEntity() instanceof Player) {
            return !this.controllableEntity.getMind().getAttribute(InteractAttribute.class).call(this.controllableEntity, damageSource.getEntity().getBukkitEntity(), Action.LEFT_CLICK).isCancelled();
        }
        return super.damageEntity(damageSource, v);
    }

    @Override
    public void e(float xMotion, float zMotion) {
        float[] motion = new float[]{xMotion, (float) this.motY, zMotion};
        if (this.controllableEntity != null) {
            ControlledRidingAttribute controlledRidingAttribute = this.controllableEntity.getMind().getAttribute(ControlledRidingAttribute.class);
            if (controlledRidingAttribute != null) {
                controlledRidingAttribute.onRide(motion);
            }
        }
        this.motY = motion[1];
        super.e(motion[0], motion[2]);
    }

    @Override
    public void g(double x, double y, double z) {
        if (this.controllableEntity != null) {
            Vector velocity = this.controllableEntity.getMind().getAttribute(PushAttribute.class).call(this.controllableEntity, new Vector(x, y, z)).getPushVelocity();
            x = velocity.getX();
            y = velocity.getY();
            z = velocity.getZ();
        }
        super.g(x, y, z);
    }

    @Override
    public void die(DamageSource damagesource) {
        if (this.controllableEntity != null) {
            this.controllableEntity.getEntityManager().despawn(this.controllableEntity, DespawnReason.DEATH);
        }
        super.die(damagesource);
    }

    @Override
    public org.bukkit.Material getDefaultLoot() {
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

    /*
     * Other stuff
     * animations, sounds, etc.
     */

    private String getSoundFor(EntitySound soundType, String def) {
        return this.controllableEntity == null ? def : this.controllableEntity.getSound(soundType);
    }

    private String getSoundFor(EntitySound soundType, String def, String key) {
        return this.controllableEntity == null ? def : this.controllableEntity.getSound(soundType, key);
    }

    // Catch any other sounds that are deep in NMS methods
    @Override
    public void makeSound(String s, float f, float f1) {
        switch (s) {
            case "mob.horse.jump":
                s = this.getSoundFor(EntitySound.JUMP, s);
                break;
            case "mob.horse.land":
                this.getSoundFor(EntitySound.STEP, s, "land");
                break;
            case "mob.horse.breathe":
                s = this.getSoundFor(EntitySound.STEP, s, "armor");
                break;
            case "mob.horse.gallop":
                s = this.getSoundFor(EntitySound.STEP, s, "gallop");
                break;
            case "mob.horse.wood":
                s = this.getSoundFor(EntitySound.STEP, s, "wood");
                break;
            case "mob.horse.soft":
                s = this.getSoundFor(EntitySound.STEP, s, "soft");
                break;
            case "mob.horse.armor":
                s = this.getSoundFor(EntitySound.STEP, s, "armor");
                break;
            case "mob.horse.leather":
                s = this.getSoundFor(EntitySound.STEP, s, "leatherarmor");
                break;
        }
        super.makeSound(s, f, f1);
    }

    private void cQ() {
        // Possibly just call the method instead...But that won't work with Cauldron :\
        if (!this.world.isStatic) {
            // int bE
            SafeField<Integer> field_bE = (SafeField<Integer>) new Reflection().reflect(EntityHorse.class).getSafeFields(withExactType(Integer.class)).get(1);
            field_bE.getAccessor().set(this, 1);

            // Open the horse's mouth (animation 128)
            this.horseVisual(128, true);
        }
    }

    private void cS() {
        if (!this.world.isStatic) {
            // int bF
            SafeField<Integer> field_bF = (SafeField<Integer>) new Reflection().reflect(EntityHorse.class).getSafeFields(withExactType(Integer.class)).get(2);
            field_bF.getAccessor().set(this, 1);

            // Stop looking down (animation 32)
            this.p(true);
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