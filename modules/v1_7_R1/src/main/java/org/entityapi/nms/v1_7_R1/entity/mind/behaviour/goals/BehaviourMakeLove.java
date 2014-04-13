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

package org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_7_R1.EntityVillager;
import net.minecraft.server.v1_7_R1.Vec3D;
import net.minecraft.server.v1_7_R1.Village;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.RandomPositionGenerator;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

import java.util.Iterator;
import java.util.List;

public class BehaviourMakeLove extends BehaviourBase {

    private EntityVillager mate;
    private int ticks;
    private Village village;
    private double navigationSpeed;

    public BehaviourMakeLove(ControllableEntity controllableEntity) {
        this(controllableEntity, 0.25D);
    }

    public BehaviourMakeLove(ControllableEntity controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public ControllableEntity<? extends Villager> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityVillager getHandle() {
        return (EntityVillager) BasicEntityUtil.getInstance().getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
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
            this.getControllableEntity().navigateTo((LivingEntity) this.mate.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
            NMSEntityUtil.getNavigation(this.getHandle()).a(this.mate, 0.25D);
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