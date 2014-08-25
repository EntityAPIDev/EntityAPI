/*
 * Copyright (C) EntityAPI Team
 *
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

import net.minecraft.server.v1_7_R1.Container;
import net.minecraft.server.v1_7_R1.EntityHuman;
import net.minecraft.server.v1_7_R1.EntityVillager;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.api.entity.type.ControllableVillager;
import org.entityapi.nms.v1_7_R1.NMSEntityUtil;
import org.entityapi.nms.v1_7_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalTradeWithPlayer<T extends ControllableVillager> extends BehaviourGoalBase<T, EntityVillager> {

    public BehaviourGoalTradeWithPlayer(T controllableEntity) {
        super(controllableEntity);
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.IMPULSE;
    }

    @Override
    public String getDefaultKey() {
        return "Trade With Player";
    }

    @Override
    public boolean shouldStart() {
        if (!this.getHandle().isAlive()) {
            return false;
        } else if (this.getHandle().M()) {
            return false;
        } else if (!this.getHandle().onGround) {
            return false;
        } else if (this.getHandle().velocityChanged) {
            return false;
        } else {
            EntityHuman tradingWith = this.getHandle().b();

            return tradingWith == null ? false : (this.getHandle().e(tradingWith) > 16.0D ? false : tradingWith.activeContainer instanceof Container);
        }
    }

    @Override
    public void start() {
        NMSEntityUtil.getNavigation(this.getHandle()).h();
    }

    @Override
    public void finish() {
        this.getHandle().a_((EntityHuman) null);
    }
}