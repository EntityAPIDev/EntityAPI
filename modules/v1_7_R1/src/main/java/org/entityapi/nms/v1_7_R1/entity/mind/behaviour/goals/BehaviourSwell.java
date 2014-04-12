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

import net.minecraft.server.v1_7_R1.EntityCreeper;
import net.minecraft.server.v1_7_R1.EntityLiving;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Creeper;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.BasicEntityUtil;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourSwell extends BehaviourBase {

    private EntityLiving target;

    public BehaviourSwell(ControllableEntity<Creeper> controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public ControllableEntity<Creeper> getControllableEntity() {
        return super.getControllableEntity();
    }

    @Override
    public EntityCreeper getHandle() {
        return (EntityCreeper) BasicEntityUtil.getInstance().getHandle(this.getControllableEntity());
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.INSTINCT;
    }

    @Override
    public String getDefaultKey() {
        return "Swell";
    }

    @Override
    public boolean shouldStart() {
        EntityLiving entityliving = ((CraftLivingEntity) this.getControllableEntity().getTarget()).getHandle();
        return this.getHandle().bZ() > 0 || entityliving != null && this.getHandle().e(entityliving) < 9.0D;
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.getHandle()).h();
        this.target = this.getHandle().getGoalTarget();
    }

    @Override
    public void finish() {
        this.target = null;
    }

    @Override
    public void tick() {
        if (this.target == null) {
            this.getHandle().a(-1);
        } else if (this.getHandle().e(this.target) > 49.0D) {
            this.getHandle().a(-1);
        } else if (!this.getHandle().getEntitySenses().canSee(this.target)) {
            this.getHandle().a(-1);
        } else {
            this.getHandle().a(1);
        }
    }
}