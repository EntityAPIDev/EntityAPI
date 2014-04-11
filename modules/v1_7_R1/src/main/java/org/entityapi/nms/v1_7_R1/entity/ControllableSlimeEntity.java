/*
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
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.api.EntitySound;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.api.mind.attribute.RideControlAttribute;
import org.entityapi.nms.v1_7_R1.reflection.PathfinderGoalSelectorRef;

public class ControllableSlimeEntity extends EntitySlime implements ControllableEntityHandle {

    private final ControllableEntity controllableEntity;

    public ControllableSlimeEntity(World world, ControllableEntity controllableEntity) {
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
            RideControlAttribute rideControlAttribute = this.controllableEntity.getMind().getAttribute(RideControlAttribute.class);
            if (rideControlAttribute != null) {
                rideControlAttribute.onRide(motion);
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
    protected String bT() {
        return this.controllableEntity == null ? "mob.slime." + (this.getSize() > 1 ? "big" : "small") : this.controllableEntity.getSound(EntitySound.IDLE, (this.getSize() > 1 ? "big" : "small"));
    }

    @Override
    protected String aT() {
        return this.controllableEntity == null ? "mob.slime." + (this.getSize() > 1 ? "big" : "small") : this.controllableEntity.getSound(EntitySound.HURT, (this.getSize() > 1 ? "big" : "small"));
    }

    @Override
    protected String aU() {
        return this.controllableEntity == null ? "mob.slime." + (this.getSize() > 1 ? "big" : "small") : this.controllableEntity.getSound(EntitySound.DEATH, (this.getSize() > 1 ? "big" : "small"));
    }

    @Override
    public void makeSound(String s, float f, float f1) {
        if (s.equals("mob.attack")) {
            if (this.controllableEntity != null) {
                s = this.controllableEntity.getSound(EntitySound.ATTACK);
            }
        }
        super.makeSound(s, f, f1);
    }
}