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
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.attribute.TamingAttribute;
import org.entityapi.api.mind.behaviour.BehaviourType;

public class BehaviourTamerHurtTarget extends BehaviourTarget {

    private EntityLiving target;
    private int lastAttackTick;

    public BehaviourTamerHurtTarget(ControllableEntity controllableEntity) {
        super(controllableEntity, false);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Tamer Hurt Target";
    }

    private boolean isTamed() {
        if (this.getHandle() instanceof EntityTameableAnimal) {
            return ((EntityTameableAnimal) this.getHandle()).isTamed();
        }
        TamingAttribute tamingAttribute = this.getControllableEntity().getMind().getAttribute(TamingAttribute.class);
        if (tamingAttribute != null) {
            return tamingAttribute.isTamed();
        }
        return false;
    }

    private EntityLiving getTamer() {
        if (this.getHandle() instanceof EntityTameableAnimal) {
            return ((EntityTameableAnimal) this.getHandle()).getOwner();
        }
        TamingAttribute tamingAttribute = this.getControllableEntity().getMind().getAttribute(TamingAttribute.class);
        if (tamingAttribute != null) {
            return ((CraftLivingEntity) tamingAttribute.getTamer()).getHandle();
        }
        return null;
    }

    @Override
    public boolean shouldStart() {
        if (!this.isTamed()) {
            return false;
        } else {
            EntityLiving tamer = this.getTamer();

            if (tamer == null) {
                return false;
            } else {
                this.target = tamer.aL();
                int i = tamer.aM();

                return i != this.lastAttackTick && this.isSuitableTarget(this.target, false);
            }
        }
    }

    @Override
    public void start() {
        this.getControllableEntity().setTarget((LivingEntity) this.target.getBukkitEntity());

        if (this.getTamer() != null) {
            this.lastAttackTick = this.getTamer().aM();
        }

        super.start();
    }

    @Override
    public void tick() {

    }
}