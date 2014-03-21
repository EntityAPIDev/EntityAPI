package org.entityapi.api.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.*;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.entity.selector.EntitySelectorViewable;
import org.entityapi.nms.NMSEntityUtil;

import java.util.List;

/**
 * Makes the entity avoid any other entity of the given class type
 */

public class BehaviourAvoidEntity extends Behaviour {

    private EntitySelectorViewable selector = new EntitySelectorViewable(this);
    private Class<?> classToAvoid;
    private float f;
    private double speedWhenFar;
    private double speedWhenNear;
    private Entity entityToAvoid;
    private PathEntity path;

    public BehaviourAvoidEntity(ControllableEntity controllableEntity, Class<?> classToAvoid, float minDistance, double speedWhenFar, double speedWhenNear) {
        super(controllableEntity);
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
        return "Avoid Entity";
    }

    @Override
    public boolean shouldStart() {
        if (this.classToAvoid == EntityHuman.class) {
            if (this.getHandle() instanceof EntityTameableAnimal && ((EntityTameableAnimal) this.getHandle()).isTamed()) {
                return false;
            }

            this.entityToAvoid = this.getHandle().world.findNearbyPlayer(this.getHandle(), (double) this.f);
            if (this.entityToAvoid == null) {
                return false;
            }
        } else {
            List list = this.getHandle().world.a(this.classToAvoid, this.getHandle().boundingBox.grow((double) this.f, 3.0D, (double) this.f), this.selector);

            if (list.isEmpty()) {
                return false;
            }

            this.entityToAvoid = (Entity) list.get(0);
        }

        Vec3D vec3d = org.entityapi.nms.RandomPositionGenerator.b(this.getHandle(), 16, 7, this.getHandle().world.getVec3DPool().create(this.entityToAvoid.locX, this.entityToAvoid.locY, this.entityToAvoid.locZ));

        if (vec3d == null) {
            return false;
        } else if (this.entityToAvoid.e(vec3d.c, vec3d.d, vec3d.e) < this.entityToAvoid.e(this.getHandle())) {
            return false;
        } else {
            this.path = NMSEntityUtil.getNavigation(this.getHandle()).a(vec3d.c, vec3d.d, vec3d.e);
            return this.path == null ? false : this.path.b(vec3d);
        }
    }

    @Override
    public boolean shouldContinue() {
        return !NMSEntityUtil.getNavigation(this.getHandle()).g();
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.getHandle()).a(this.path, this.speedWhenFar);
    }

    @Override
    public void finish() {
        this.entityToAvoid = null;
    }

    @Override
    public void tick() {
        if (this.getHandle().e(this.entityToAvoid) < 49.0D) {
            NMSEntityUtil.getNavigation(this.getHandle()).a(this.speedWhenNear);
        } else {
            NMSEntityUtil.getNavigation(this.getHandle()).a(this.speedWhenFar);
        }
    }

    public static ControllableEntity getEntityFor(BehaviourAvoidEntity behaviourAvoidEntity) {
        return behaviourAvoidEntity.getControllableEntity();
    }
}