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

import net.minecraft.server.v1_7_R1.EntityVillager;
import org.bukkit.craftbukkit.v1_7_R1.entity.CraftLivingEntity;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.attribute.TradingAttribute;

import java.util.List;

public class BehaviourGoalLookAtTradingPlayer extends BehaviourGoalLookAtNearestEntity {

    public BehaviourGoalLookAtTradingPlayer(ControllableEntity controllableEntity, float minDistance) {
        super(controllableEntity, HumanEntity.class, minDistance, 0.02F);
    }

    @Override
    public String getDefaultKey() {
        return "Look Trading Player";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle() instanceof EntityVillager) {
            if (((EntityVillager) this.getHandle()).ca()) {
                this.target = ((EntityVillager) this.getHandle()).b();
                return true;
            } else return false;
        } else {
            TradingAttribute tradingAttribute = this.getControllableEntity().getMind().getAttribute(TradingAttribute.class);
            if (tradingAttribute != null) {
                List<Player> tradingPlayers = tradingAttribute.getTradingPlayers();
                if (tradingPlayers.size() != 0) {
                    this.target = ((CraftLivingEntity) tradingPlayers.get(tradingPlayers.size() - 1)).getHandle();
                }
            }
        }
        return false;
    }
}