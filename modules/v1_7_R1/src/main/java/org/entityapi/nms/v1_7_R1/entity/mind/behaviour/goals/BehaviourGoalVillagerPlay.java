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

import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.EntityVillager;
import net.minecraft.server.v1_7_R1.Vec3D;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Villager;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.RandomPositionGenerator;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

import java.util.Iterator;
import java.util.List;

public class BehaviourGoalVillagerPlay extends BehaviourGoalBase {

    private EntityLiving playMate;
    private int playTicks;
    private double navigationSpeed;

    public BehaviourGoalVillagerPlay(ControllableEntity<? extends Villager> controllableEntity, double navigationSpeed) {
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
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Villager Play";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle().getAge() >= 0) {
            return false;
        } else if (this.getHandle().aI().nextInt(400) != 0) {
            return false;
        } else {
            List nearbyVillagers = this.getHandle().world.a(EntityVillager.class, this.getHandle().boundingBox.grow(6.0D, 3.0D, 6.0D));
            double minDistance = Double.MAX_VALUE;
            Iterator iterator = nearbyVillagers.iterator();

            while (iterator.hasNext()) {
                EntityVillager closest = (EntityVillager) iterator.next();

                if (closest != this.getHandle() && !closest.bZ() && closest.getAge() < 0) {
                    double distance = closest.e(this.getHandle());

                    if (distance <= minDistance) {
                        minDistance = distance;
                        this.playMate = closest;
                    }
                }
            }

            if (this.playMate == null) {
                Vec3D vec3d = RandomPositionGenerator.a(this.getHandle(), 16, 3);

                if (vec3d == null) {
                    return false;
                }
            }

            return true;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.playTicks > 0;
    }

    @Override
    public void start() {
        if (this.playMate != null) {
            this.getHandle().j(true);
        }

        this.playTicks = 1000;
    }

    @Override
    public void finish() {
        this.getHandle().j(false);
        this.playMate = null;
    }

    @Override
    public void tick() {
        --this.playTicks;
        if (this.playMate != null) {
            if (this.getHandle().e(this.playMate) > 4.0D) {
                this.getControllableEntity().navigateTo((LivingEntity) this.playMate.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
            }
        } else if (NMSEntityUtil.getNavigation(this.getHandle()).g()) {
            Vec3D vec3d = RandomPositionGenerator.a(this.getHandle(), 16, 3);

            if (vec3d == null) {
                return;
            }

            this.getControllableEntity().navigateTo(new Vector(vec3d.c, vec3d.d, vec3d.e), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
        }
    }
}