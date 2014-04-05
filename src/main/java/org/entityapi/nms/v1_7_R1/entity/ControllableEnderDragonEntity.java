package org.entityapi.nms.v1_7_R1.entity;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.EntityAPICore;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.api.EntitySound;
import org.entityapi.api.mind.Attribute;
import org.entityapi.nms.v1_7_R1.entity.mind.attribute.RideAttribute;
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
            EntityAPICore.callOnTick(this.controllableEntity);
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

        if (EntityAPICore.callOnCollide(this.controllableEntity, entity.getBukkitEntity())) {
            super.collide(entity);
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        if (this.controllableEntity == null || !(entity.getBukkitEntity() instanceof Player)) {
            return super.c(entity);
        }

        return EntityAPICore.callOnInteract(this.controllableEntity, (Player) entity.getBukkitEntity(), true);
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float v) {
        if (this.controllableEntity != null && damageSource.getEntity() != null && damageSource.getEntity().getBukkitEntity() instanceof Player) {
            EntityAPICore.callOnInteract(this.controllableEntity, (Player) damageSource.getEntity(), false);
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
            Vector velocity = EntityAPICore.callOnPush(this.controllableEntity, x, y, z);
            x = velocity.getX();
            y = velocity.getY();
            z = velocity.getZ();
        }
        super.g(x, y, z);
    }

    @Override
    public void die(DamageSource damagesource) {
        if (this.controllableEntity != null) {
            EntityAPICore.callOnDeath(this.controllableEntity);
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