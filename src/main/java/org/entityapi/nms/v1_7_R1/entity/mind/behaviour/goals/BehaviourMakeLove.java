package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityVillager;
import net.minecraft.server.v1_7_R1.Vec3D;
import net.minecraft.server.v1_7_R1.Village;
import org.bukkit.entity.Villager;
import org.entityapi.EntityAPICore;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.RandomPositionGenerator;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

import java.util.Iterator;
import java.util.List;

public class BehaviourMakeLove extends BehaviourBase {

    private EntityVillager mate;
    private int ticks;
    private Village village;

    public BehaviourMakeLove(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public ControllableEntity<Villager> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityVillager getHandle() {
        return (EntityVillager) ((BasicEntityUtil) EntityAPICore.getBasicEntityUtil()).getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Make Love";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().getAge() >= 0) {
            return false;
        } else if (this.getHandle().aI().nextInt(400) != 0) {
            return false;
        } else {
            List nearby = this.getHandle().world.a(EntityVillager.class, this.getHandle().boundingBox.grow(6.0D, 3.0D, 6.0D));
            double minDist = Double.MAX_VALUE;
            Iterator iterator = nearby.iterator();

            while (iterator.hasNext()) {
                EntityVillager possibleMate = (EntityVillager) iterator.next();

                if (possibleMate != this.getHandle() && !possibleMate.bZ() && possibleMate.getAge() < 0) {
                    double dist = possibleMate.e(this.getHandle());

                    if (dist <= minDist) {
                        minDist = dist;
                        this.mate = possibleMate;
                    }
                }
            }

            if (this.mate == null) {
                Vec3D randomPosition = RandomPositionGenerator.a(this.getHandle(), 16, 3);

                if (randomPosition == null) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.ticks >= 0 && this.f() && this.getHandle().getAge() == 0;
    }

    @Override
    public void start() {
        this.ticks = 300;
        this.getHandle().i(true);
    }

    @Override
    public void finish() {
        this.village = null;
        this.mate = null;
        this.getHandle().i(false);
    }

    @Override
    public void tick() {
        --this.ticks;
        this.getHandle().getControllerLook().a(this.mate, 10.0F, 30.0F);
        if (this.getHandle().e(this.mate) > 2.25D) {
            this.getHandle().getNavigation().a((Entity) this.mate, 0.25D);
        } else if (this.ticks == 0 && this.mate.bY()) {
            this.createBaby();
        }

        if (this.getHandle().aI().nextInt(35) == 0) {
            this.getHandle().world.broadcastEntityEffect(this.getHandle(), (byte) 12);
        }
    }

    private boolean f() {
        if (!this.village.i()) {
            return false;
        } else {
            int population = (int) ((double) ((float) this.village.getDoorCount()) * 0.35D);

            return this.village.getPopulationCount() < population;
        }
    }

    private void createBaby() {
        EntityVillager baby = this.getHandle().b(this.mate);
        this.mate.setAge(6000);
        this.getHandle().setAge(6000);
        baby.setAge(-24000);
        baby.setPositionRotation(this.getHandle().locX, this.getHandle().locY, this.getHandle().locZ, 0.0F, 0.0F);
        this.getHandle().world.addEntity(baby, org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason.BREEDING); // CraftBukkit - added SpawnReason
        this.getHandle().world.broadcastEntityEffect(baby, (byte) 12);
    }
}