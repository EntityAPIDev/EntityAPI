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

package org.entityapi.nms.v1_8_R1.entity;

import net.minecraft.server.v1_8_R1.*;
import org.bukkit.craftbukkit.v1_8_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.attribute.*;
import org.entityapi.api.entity.type.ControllableBlaze;
import org.entityapi.api.entity.type.nms.ControllableBlazeHandle;
import org.entityapi.api.events.Action;
import org.entityapi.api.utils.EntityUtil;

public class ControllableBlazeEntity extends EntityBlaze implements ControllableBlazeHandle {

    private final ControllableBlaze controllableEntity;

    public ControllableBlazeEntity(World world, ControllableBlaze controllableEntity) {
        super(world);
        this.controllableEntity = controllableEntity;
        EntityUtil.clearGoals(this);
    }

    @Override
    public ControllableBlaze getControllableEntity() {
        return this.controllableEntity;
    }

    // All ControllableEntities should use new AI
    @Override
    protected boolean bk() {
        return true;
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
        return this.controllableEntity == null ? "mob.blaze.idle" : this.controllableEntity.getSound(EntitySound.IDLE);
    }

    @Override
    protected String aT() {
        return this.controllableEntity == null ? "mob.blaze.hurt" : this.controllableEntity.getSound(EntitySound.HURT);
    }

    @Override
    protected String aU() {
        return this.controllableEntity == null ? "mob.blaze.death" : this.controllableEntity.getSound(EntitySound.DEATH);
    }
}