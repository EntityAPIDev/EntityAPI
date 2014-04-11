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

import org.entityapi.api.ControllableEntity;
import org.entityapi.api.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourBase;

public class BehaviourLookAtRandom extends BehaviourBase {

    private double xDiff;
    private double yDiff;
    private int lookTicks;

    public BehaviourLookAtRandom(ControllableEntity controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.THREE;
    }

    @Override
    public String getDefaultKey() {
        return "Look At Random";
    }

    @Override
    public boolean shouldStart() {
        return this.getHandle().aI().nextFloat() < 0.02F;
    }

    @Override
    public boolean shouldContinue() {
        return this.lookTicks >= 0;
    }

    @Override
    public void start() {
        double d0 = 6.283185307179586D * this.getHandle().aI().nextDouble();

        this.xDiff = Math.cos(d0);
        this.yDiff = Math.sin(d0);
        this.lookTicks = 20 + this.getHandle().aI().nextInt(20);
    }

    @Override
    public void tick() {
        --this.lookTicks;
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.getHandle().locX + this.xDiff, this.getHandle().locY + (double) this.getHandle().getHeadHeight(), this.getHandle().locZ + this.yDiff, 10.0F, (float) NMSEntityUtil.getMaxHeadRotation(this.getHandle()));
    }
}