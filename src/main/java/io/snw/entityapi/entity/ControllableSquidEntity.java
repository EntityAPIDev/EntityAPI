package io.snw.entityapi.entity;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.ControllableEntityHandle;
import io.snw.entityapi.api.mind.attribute.Attribute;
import io.snw.entityapi.api.mind.attribute.RideAttribute;
import io.snw.entityapi.reflection.refs.PathfinderGoalSelectorRef;
import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;

public class ControllableSquidEntity extends EntitySquid implements ControllableEntityHandle {

    private final ControllableEntity controllableEntity;

    public ControllableSquidEntity(World world, ControllableEntity controllableEntity) {
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
            this.controllableEntity.onTick();
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

        if (this.controllableEntity.onCollide(entity.getBukkitEntity())) {
            super.collide(entity);
        }
    }

    @Override
    public boolean a(EntityHuman entity) {
        if (this.controllableEntity == null || !(entity.getBukkitEntity() instanceof Player)) {
            return super.c(entity);
        }

        return controllableEntity.onInteract((Player) entity.getBukkitEntity(), true);
    }

    @Override
    public boolean damageEntity(DamageSource damageSource, float v) {
        if (this.controllableEntity != null && damageSource.getEntity() != null && damageSource.getEntity().getBukkitEntity() instanceof Player) {
            this.controllableEntity.onInteract((Player) damageSource.getEntity(), false);
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
            this.controllableEntity.onDeath();
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
}