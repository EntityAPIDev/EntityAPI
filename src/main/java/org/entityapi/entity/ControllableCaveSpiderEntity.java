package org.entityapi.entity;

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.util.CraftMagicNumbers;
import org.bukkit.entity.Player;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.api.EntitySound;
import org.entityapi.api.mind.attribute.Attribute;
import org.entityapi.api.mind.attribute.RideAttribute;
import org.entityapi.reflection.refs.PathfinderGoalSelectorRef;

public class ControllableCaveSpiderEntity extends EntityCaveSpider implements ControllableEntityHandle {

    private final ControllableEntity controllableEntity;

    public ControllableCaveSpiderEntity(World world, ControllableEntity controllableEntity) {
        super(world);
        this.controllableEntity = controllableEntity;
        new PathfinderGoalSelectorRef(this).clearGoals();
    }

    @Override
    public ControllableEntity getControllableEntity() {
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
    public void h() {
        super.h();
        if (this.controllableEntity != null) {
            ((ControllableBaseEntity) this.controllableEntity).onTick();
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
        return this.controllableEntity == null ? "mob.spider.say" : this.controllableEntity.getSound(EntitySound.IDLE);
    }

    @Override
    protected String aT() {
        return this.controllableEntity == null ? "mob.spider.say" : this.controllableEntity.getSound(EntitySound.HURT);
    }

    @Override
    protected String aU() {
        return this.controllableEntity == null ? "mob.spider.death" : this.controllableEntity.getSound(EntitySound.DEATH);
    }

    @Override
    protected void a(int i, int j, int k, Block block) {
        this.makeSound(this.controllableEntity == null ? "mob.spider.step" : this.controllableEntity.getSound(EntitySound.STEP), 0.15F, 1.0F);
    }
}