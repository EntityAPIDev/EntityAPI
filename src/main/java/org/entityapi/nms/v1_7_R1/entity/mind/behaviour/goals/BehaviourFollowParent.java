package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityAnimal;
import org.bukkit.entity.Animals;
import org.entityapi.EntityAPICore;
import org.entityapi.api.ControllableEntity;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;
import org.entityapi.api.mind.BehaviourType;

import java.util.Iterator;
import java.util.List;

public class BehaviourFollowParent extends BehaviourBase {

    private EntityAnimal parent;
    private int followTicks;

    public BehaviourFollowParent(ControllableEntity<Animals> controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public ControllableEntity<Animals> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityAnimal getHandle() {
        return (EntityAnimal) ((BasicEntityUtil) EntityAPICore.getBasicEntityUtil()).getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ZERO;
    }

    @Override
    public String getDefaultKey() {
        return "Follow Parent";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().getAge() >= 0) {
            return false;
        } else {
            List nearbyAnimals = this.getHandle().world.a(this.getHandle().getClass(), this.getHandle().boundingBox.grow(8.0D, 4.0D, 8.0D));
            EntityAnimal nearest = null;
            double minDistance = Double.MAX_VALUE;
            Iterator iterator = nearbyAnimals.iterator();

            while (iterator.hasNext()) {
                EntityAnimal animal = (EntityAnimal) iterator.next();

                if (animal.getAge() >= 0) {
                    double dist = this.getHandle().e(animal);

                    if (dist <= minDistance) {
                        minDistance = dist;
                        nearest = animal;
                    }
                }
            }

            if (nearest == null) {
                return false;
            } else if (minDistance < 9.0D) {
                return false;
            } else {
                this.parent = nearest;
                return true;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        if (!this.parent.isAlive()) {
            return false;
        } else {
            double dist = this.getHandle().e(this.parent);

            return dist >= 9.0D && dist <= 256.0D;
        }
    }

    @Override
    public void start() {
        this.followTicks = 0;
    }

    @Override
    public void finish() {
        this.parent = null;
    }

    @Override
    public void tick() {
        if (--this.followTicks <= 0) {
            this.followTicks = 10;
            this.getHandle().getNavigation().a(this.parent, this.getControllableEntity().getSpeed());
        }
    }
}