package org.entityapi.entity;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.api.EntitySound;
import org.entityapi.api.mind.attribute.Attribute;
import org.entityapi.api.mind.attribute.RideAttribute;
import org.entityapi.reflection.refs.PathfinderGoalSelectorRef;

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
        if (this.controllableEntity == null && !(this.controllableEntity instanceof ControllableEnderDragon)) {
            return null;
        }
        return ((ControllableEnderDragon) this.controllableEntity).getTargetPosition();
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
            ((ControllableBaseEntity) this.controllableEntity).onTick();
            if (this.controllableEntity.shouldUpdateAttributes()) {
                this.controllableEntity.getMind().tick();
            }
        }
        if (this.getTargetPosition() != null) {
            if (this.controllableEntity instanceof ControllableEnderDragon) {
                if (((ControllableEnderDragon) this.controllableEntity).shouldUseAppliedTargetPosition()) {
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

        if (((ControllableBaseEntity) this.controllableEntity).onCollide(entity.getBukkitEntity())) {
            super.collide(entity);
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        if (this.controllableEntity == null || !(entity.getBukkitEntity() instanceof Player)) {
            return super.c(entity);
        }

        return ((ControllableBaseEntity) this.controllableEntity).onInteract((Player) entity.getBukkitEntity(), true);
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float v) {
        if (this.controllableEntity != null && damageSource.getEntity() != null && damageSource.getEntity().getBukkitEntity() instanceof Player) {
            ((ControllableBaseEntity) this.controllableEntity).onInteract((Player) damageSource.getEntity(), false);
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
    public void die(DamageSource damagesource) {
        if (this.controllableEntity != null) {
            ((ControllableBaseEntity) this.controllableEntity).onDeath();
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