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
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityHandle;
import org.entityapi.api.entity.EntitySound;
import org.entityapi.api.entity.mind.attribute.ControlledRidingAttribute;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.nms.v1_7_R1.reflection.PathfinderGoalSelectorRef;

//TODO: controls over stationary flying and destruction of blocks
public class ControllableEnderDragonEntity extends EntityEnderDragon implements ControllableEntityHandle {

    private final ControllableEntity controllableEntity;

    public ControllableEnderDragonEntity(World world, ControllableEntity controllableEntity) {
        super(world);
        this.controllableEntity = controllableEntity;
        new PathfinderGoalSelectorRef(this).clearGoals();
    }

    @Override
    public ControllableEntity getControllableEntity() {
        return this.controllableEntity;
    }

    public Vector getTargetPosition() {
        if (this.controllableEntity == null && !(this.controllableEntity instanceof ControllableEnderDragonBase)) {
            return null;
        }
        return ((ControllableEnderDragonBase) this.controllableEntity).getTargetPosition();
    }

    @Override
    public org.bukkit.Material getDefaultMaterialLoot() {
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
            EntityAPI.getCore().callOnTick(this.controllableEntity);
            if (this.controllableEntity.shouldUpdateAttributes()) {
                this.controllableEntity.getMind().tick();
            }
        }
        if (this.getTargetPosition() != null) {
            if (this.controllableEntity instanceof ControllableEnderDragonBase) {
                if (((ControllableEnderDragonBase) this.controllableEntity).isUsingAppliedTargetPosition()) {
                    this.h = this.getTargetPosition().getX();
                    this.i = this.getTargetPosition().getY();
                    this.j = this.getTargetPosition().getZ();
                }
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