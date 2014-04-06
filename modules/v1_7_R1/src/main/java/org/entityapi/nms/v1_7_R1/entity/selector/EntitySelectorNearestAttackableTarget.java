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

package org.entityapi.nms.v1_7_R1.entity.selector;

import net.minecraft.server.v1_7_R1.Entity;
import net.minecraft.server.v1_7_R1.EntityLiving;
import net.minecraft.server.v1_7_R1.IEntitySelector;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.goals.BehaviourMoveTowardsNearestAttackableTarget;

public class EntitySelectorNearestAttackableTarget implements IEntitySelector {

    final IEntitySelector selector;

    final BehaviourMoveTowardsNearestAttackableTarget behaviour;

    public EntitySelectorNearestAttackableTarget(BehaviourMoveTowardsNearestAttackableTarget behaviour, IEntitySelector ientityselector) {
        this.behaviour = behaviour;
        this.selector = ientityselector;
    }

    @Override
    public boolean a(Entity entity) {
        return !(entity instanceof EntityLiving) ? false : (this.selector != null && !this.selector.a(entity) ? false : this.behaviour.isSuitableTarget((EntityLiving) entity, false));
    }
}