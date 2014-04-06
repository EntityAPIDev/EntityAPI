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
import net.minecraft.server.v1_7_R1.EntityTameableAnimal;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.BehaviourType;

public class BehaviourDefendOwner extends BehaviourTarget {

    private EntityLiving target;
    private int lastAttackTick;

    public BehaviourDefendOwner(ControllableEntity controllableEntity) {
        this(controllableEntity, false, false);
    }

    public BehaviourDefendOwner(ControllableEntity controllableEntity, boolean checkSenses, boolean useMelee) {
        super(controllableEntity, checkSenses, useMelee);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ONE;
    }

    @Override
    public String getDefaultKey() {
        return "Owner Hurt By Target";
    }

    private boolean isTamed() {
        if (this.getHandle() instanceof EntityTameableAnimal) {
            return ((EntityTameableAnimal) this.getHandle()).isTamed();
        }
        return false;
    }

    private EntityLiving getOwner() {
        return null; // TODO!!!
    }

    @Override
    public boolean shouldStart() {
        // TODO: Taming Attribute
        if (!this.isTamed()) {
            return false;
        } else {
            EntityLiving owner = this.getOwner();

            if (owner == null) {
                return false;
            } else {
                this.target = owner.getLastDamager();
                int lastAttackTick = owner.aK();

                return lastAttackTick != this.lastAttackTick && this.isSuitableTarget(this.target, false);
            }
        }
    }

    @Override
    public void tick() {
        this.getControllableEntity().setTarget((LivingEntity) this.target.getBukkitEntity());

        EntityLiving owner = this.getOwner();
        if (owner != null) {
            this.lastAttackTick = owner.aK();
        }

        super.tick();
    }
}