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

import net.minecraft.server.v1_7_R1.*;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.api.reflection.refs.NMSEntityClassRef;
import org.entityapi.nms.v1_7_R1.entity.selector.EntitySelectorNearestAttackableTarget;

import java.util.Collections;
import java.util.List;

/**
 * Moves the entity towards the nearest attackable target
 */

public class BehaviourMoveTowardsNearestAttackableTarget extends BehaviourTarget {

    private final Class<? extends Entity> classToTarget;
    private final int chance;
    private final DistanceComparator distComparator;
    private final IEntitySelector selector;
    private EntityLiving target;

    public BehaviourMoveTowardsNearestAttackableTarget(ControllableEntity controllableEntity, Class<? extends org.bukkit.entity.Entity> classToTarget, int chance, boolean checkSenses) {
        this(controllableEntity, classToTarget, chance, checkSenses, false);
    }

    public BehaviourMoveTowardsNearestAttackableTarget(ControllableEntity controllableEntity, Class<? extends org.bukkit.entity.Entity> classToTarget, int chance, boolean checkSenses, boolean useMelee) {
        this(controllableEntity, classToTarget, chance, checkSenses, useMelee, null);
    }

    public BehaviourMoveTowardsNearestAttackableTarget(ControllableEntity controllableEntity, Class<? extends org.bukkit.entity.Entity> classToTarget, int chance, boolean checkSenses, boolean useMelee, IEntitySelector selector) {
        super(controllableEntity, checkSenses, useMelee);
        this.classToTarget = (Class<? extends Entity>) NMSEntityClassRef.getNMSClass(classToTarget);
        if (this.classToTarget == null && !(EntityLiving.class.isAssignableFrom(classToTarget))) {
            throw new IllegalArgumentException("Could not find valid NMS class for " + classToTarget.getSimpleName());
        }
        this.chance = chance;
        this.distComparator = new DistanceComparator(this.getHandle());
        this.selector = new EntitySelectorNearestAttackableTarget(this, selector);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Move Nearest Target";
    }

    @Override
    public boolean shouldStart() {
        if (this.chance > 0 && this.getHandle().aI().nextInt(this.chance) != 0) {
            return false;
        } else {
            double range = this.getControllableEntity().getPathfindingRange();
            List list = this.getHandle().world.a(this.classToTarget, this.getHandle().boundingBox.grow(range, 4.0D, range), this.selector);

            Collections.sort(list, this.distComparator);
            if (list.isEmpty()) {
                return false;
            } else {
                this.target = (EntityLiving) list.get(0);
                return true;
            }
        }
    }

    @Override
    public void start() {
        this.getControllableEntity().setTarget((CraftLivingEntity) this.target.getBukkitEntity());
        super.start();
    }

    @Override
    public void tick() {

    }
}