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

import net.minecraft.server.v1_7_R1.AxisAlignedBB;
import net.minecraft.server.v1_7_R1.EntityCreature;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;

import java.util.Iterator;
import java.util.List;

public class BehaviourHurtByTarget extends BehaviourTarget {

    private boolean attackNearest;
    private int lastAttackTick;

    public BehaviourHurtByTarget(ControllableEntity controllableEntity, boolean attackNearest) {
        this(controllableEntity, false, false, attackNearest);
    }

    public BehaviourHurtByTarget(ControllableEntity controllableEntity, boolean checkSenses, boolean useMelee, boolean attackNearest) {
        super(controllableEntity, checkSenses, useMelee);
        this.attackNearest = attackNearest;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Hurt By Target";
    }

    @Override
    public boolean shouldStart() {
        int lastAttackTick = this.getHandle().aK();

        return lastAttackTick != this.lastAttackTick && this.isSuitableTarget(this.getHandle().getLastDamager(), false);
    }

    @Override
    public void tick() {
        this.getControllableEntity().setTarget((LivingEntity) this.getHandle().getLastDamager().getBukkitEntity());
        this.lastAttackTick = this.getHandle().aK();
        if (this.attackNearest) {
            double range = this.getControllableEntity().getPathfindingRange();
            List list = this.getHandle().world.a(this.getHandle().getClass(), AxisAlignedBB.a().a(this.getHandle().locX, this.getHandle().locY, this.getHandle().locZ, this.getHandle().locX + 1.0D, this.getHandle().locY + 1.0D, this.getHandle().locZ + 1.0D).grow(range, 10.0D, range));
            Iterator iterator = list.iterator();

            while (iterator.hasNext()) {
                EntityCreature entitycreature = (EntityCreature) iterator.next();

                if (this.getHandle() != entitycreature && entitycreature.getGoalTarget() == null && !entitycreature.c(this.getHandle().getLastDamager())) {
                    entitycreature.setGoalTarget(this.getHandle().getLastDamager());
                }
            }
        }

        super.tick();
    }
}