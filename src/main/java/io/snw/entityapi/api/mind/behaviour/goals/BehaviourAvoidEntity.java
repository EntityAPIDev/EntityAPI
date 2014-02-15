package io.snw.entityapi.api.mind.behaviour.goals;

import io.snw.entityapi.api.ControllableEntity;
import io.snw.entityapi.api.mind.behaviour.Behaviour;
import io.snw.entityapi.api.mind.behaviour.BehaviourType;
import io.snw.entityapi.entity.selector.*;
import io.snw.entityapi.nms.NMSEntityUtil;
import net.minecraft.server.v1_7_R1.*;

import java.util.List;

/**
 * Makes the entity avoid any other entity of the given class type
 */

public class BehaviourAvoidEntity extends Behaviour {

    private EntitySelectorViewable selector = new EntitySelectorViewable(this);;
    private ControllableEntity controllableEntity;
    private EntityLiving handle;
    private Class<?> classToAvoid;
    private float f;
    private double speedWhenFar;
    private double speedWhenNear;
    private Entity entityToAvoid;
    private PathEntity path;

    public BehaviourAvoidEntity(ControllableEntity controllableEntity, Class<?> classToAvoid, float minDistance, double speedWhenFar, double speedWhenNear) {
        this.controllableEntity = controllableEntity;
        this.handle = controllableEntity.getHandle();
        this.classToAvoid = classToAvoid;
        this.speedWhenFar = speedWhenFar;
        this.speedWhenNear = speedWhenNear;
        this.f = minDistance;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Avoid Player";
    }

    @Override
    public boolean shouldStart() {
        if (this.classToAvoid == EntityHuman.class) {
            if (this.handle instanceof EntityTameableAnimal && ((EntityTameableAnimal) this.handle).isTamed()) {
                return false;
            }

            this.entityToAvoid = this.handle.world.findNearbyPlayer(this.handle, (double) this.f);
            if (this.entityToAvoid == null) {
                return false;
            }
        } else {
            List list = this.handle.world.a(this.classToAvoid, this.handle.boundingBox.grow((double) this.f, 3.0D, (double) this.f), this.selector);

            if (list.isEmpty()) {
                return false;
            }

            this.entityToAvoid = (Entity) list.get(0);
        }

        Vec3D vec3d = io.snw.entityapi.nms.RandomPositionGenerator.b(this.handle, 16, 7, this.handle.world.getVec3DPool().create(this.entityToAvoid.locX, this.entityToAvoid.locY, this.entityToAvoid.locZ));

        if (vec3d == null) {
            return false;
        } else if (this.entityToAvoid.e(vec3d.c, vec3d.d, vec3d.e) < this.entityToAvoid.e(this.handle)) {
            return false;
        } else {
            this.path = NMSEntityUtil.getNavigation(this.handle).a(vec3d.c, vec3d.d, vec3d.e);
            return this.path == null ? false : this.path.b(vec3d);
        }
    }

    @Override
    public boolean shouldContinue() {
        return !NMSEntityUtil.getNavigation(this.handle).g();
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.handle).a(this.path, this.speedWhenFar);
    }

    @Override
    public void finish() {
        this.entityToAvoid = null;
    }

    @Override
    public void tick() {
        if (this.handle.e(this.entityToAvoid) < 49.0D) {
            NMSEntityUtil.getNavigation(this.handle).a(this.speedWhenNear);
        } else {
            NMSEntityUtil.getNavigation(this.handle).a(this.speedWhenFar);
        }
    }

    public static ControllableEntity getEntityFor(BehaviourAvoidEntity behaviourAvoidEntity) {
        return behaviourAvoidEntity.controllableEntity;
    }
}