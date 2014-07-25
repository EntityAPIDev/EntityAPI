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

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.impl.ControllableEnderDragonBase;
import org.entityapi.api.entity.mind.attribute.*;
import org.entityapi.api.entity.type.ControllableEnderDragon;
import org.entityapi.api.entity.type.nms.ControllableEnderDragonHandle;
import org.entityapi.api.utils.EntityUtil;

public class ControllableEnderDragonEntity extends EntityEnderDragon implements ControllableEnderDragonHandle {

    private final ControllableEnderDragon controllableEntity;

    public ControllableEnderDragonEntity(World world, ControllableEnderDragon controllableEntity) {
        super(world);
        this.controllableEntity = controllableEntity;
        EntityUtil.clearGoals(this);
    }

    @Override
    public ControllableEnderDragon getControllableEntity() {
        return this.controllableEntity;
    }

    @Override
    public Vector getTargetPosition() {
        if (this.controllableEntity == null) {
            return null;
        }
        return this.controllableEntity.getTargetPosition();
    }

    @Override
    public org.bukkit.Material getDefaultLoot() {
        return CraftMagicNumbers.getMaterial(this.getLoot());
    }

    @Override
    protected boolean bk() {
        return true;
    }

    @Override
    public void h() {
        super.h();
        if (this.controllableEntity != null) {
            this.controllableEntity.getMind().getAttribute(TickAttribute.class).call();
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

        if (!this.controllableEntity.getMind().getAttribute(CollideAttribute.class).call(entity.getBukkitEntity()).isCancelled()) {
            super.collide(entity);
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        if (this.controllableEntity == null || !(entity.getBukkitEntity() instanceof Player)) {
            return super.c(entity);
        }

        return !this.controllableEntity.getMind().getAttribute(InteractAttribute.class).call(entity.getBukkitEntity(), true).isCancelled();
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float v) {
        if (this.controllableEntity != null && damageSource.getEntity() != null && damageSource.getEntity().getBukkitEntity() instanceof Player) {
            return !this.controllableEntity.getMind().getAttribute(InteractAttribute.class).call(damageSource.getEntity().getBukkitEntity(), false).isCancelled();
        }
        return super.damageEntity(damageSource, v);
    }

    @Override
    public void e() {
        if (this.controllableEntity == null) {
            super.e();
            return;
        }

        if (this.controllableEntity.isStationary()) {
            // https://github.com/Bukkit/mc-dev/blob/0ef88a6cbdeef0cb47bf66fd892b0ce2943e8e69/net/minecraft/server/EntityEnderDragon.java#L84-L89
            if (this.getHealth() <= 0.0F) {
                float randX = (this.random.nextFloat() - 0.5F) * 8.0F;
                float randY = (this.random.nextFloat() - 0.5F) * 4.0F;
                float randZ = (this.random.nextFloat() - 0.5F) * 8.0F;
                this.world.addParticle("largeexplode", this.locX + (double) randX, this.locY + 2.0D + (double) randY, this.locZ + (double) randZ, 0.0D, 0.0D, 0.0D);
            }
        } else {
            float[] motion = new float[]{0, 0, 0};
            ControlledRidingAttribute controlledRidingAttribute = this.controllableEntity.getMind().getAttribute(ControlledRidingAttribute.class);
            if (controlledRidingAttribute != null) {
                controlledRidingAttribute.onRide(motion);
            }
            this.motY = motion[1];
            super.e(motion[0], motion[2]);
            this.controllableEntity.getNMSAccessor().setYaw(this.yaw < 0 ? this.yaw + 180 : this.yaw - 180);
            return;
        }

        if (this.controllableEntity.shouldAllowRandomFlying()) {
            super.e();
        } else {
            if (this.getTargetPosition() != null) {
                if (this.controllableEntity instanceof ControllableEnderDragonBase) {
                    if (this.controllableEntity.isUsingAppliedTargetPosition()) {
                        this.h = this.getTargetPosition().getX();
                        this.i = this.getTargetPosition().getY();
                        this.j = this.getTargetPosition().getZ();
                    }
                }
            }
        }
        super.e();
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
            Vector velocity = this.controllableEntity.getMind().getAttribute(PushAttribute.class).call(x, y, z).getPushVelocity();
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
    protected Item getLoot() {
        org.bukkit.Material lootMaterial = this.controllableEntity.getLoot();
        return this.controllableEntity == null ? super.getLoot() : lootMaterial == null ? super.getLoot() : CraftMagicNumbers.getItem(lootMaterial);
    }

    @Override
    protected String t() {
        return this.controllableEntity == null ? "mob.enderdragon.growl" : this.controllableEntity.getSound(EntitySound.IDLE);
    }

    @Override
    protected String aT() {
        return this.controllableEntity == null ? "mob.enderdragon.hit" : this.controllableEntity.getSound(EntitySound.HIT);
    }

    @Override
    public void makeSound(String s, float f, float f1) {
        if (s.equals("mob.enderdragon.wings")) {
            if (this.controllableEntity != null) {
                s = this.controllableEntity.getSound(EntitySound.WINGS);
            }
        } else if (s.equals("mob.enderdragon.end")) {
            if (this.controllableEntity != null) {
                s = this.controllableEntity.getSound(EntitySound.DEATH);
            }
        }
        super.makeSound(s, f, f1);
    }
}