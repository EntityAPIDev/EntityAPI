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

package org.entityapi.nms.v1_8_R1.entity.mind.behaviour.goals;

import net.minecraft.server.v1_8_R1.Entity;
import net.minecraft.server.v1_8_R1.EntityIronGolem;
import net.minecraft.server.v1_8_R1.EntityLiving;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.api.entity.type.ControllableIronGolem;
import org.entityapi.api.utils.EntityUtil;
import org.entityapi.nms.v1_8_R1.entity.mind.behaviour.BehaviourGoalBase;

public class BehaviourGoalOfferFlower<T extends ControllableIronGolem> extends BehaviourGoalBase<T, EntityIronGolem> {

    private Class<? extends Entity> typeToOffer;
    private EntityLiving toOffer;
    private int offerTicks;

    public BehaviourGoalOfferFlower(T controllableEntity, Class<? extends org.bukkit.entity.Entity> classToOffer) {
        super(controllableEntity);
        this.typeToOffer = (Class<? extends Entity>) EntityUtil.getNmsClassFor(classToOffer);
        if (this.typeToOffer == null || !(EntityLiving.class.isAssignableFrom(classToOffer))) {
            throw new IllegalArgumentException("Could not find valid NMS class for " + classToOffer.getSimpleName());
        }
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
    }

    @Override
    public String getDefaultKey() {
        return "Offer Flower";
    }

    @Override
    public boolean shouldStart() {
        if (!this.getHandle().world.v()) {
            return false;
        } else if (this.getHandle().aI().nextInt(8000) != 0) {
            return false;
        } else {
            this.toOffer = (EntityLiving) this.getHandle().world.a(this.typeToOffer, this.getHandle().boundingBox.grow(6.0D, 2.0D, 6.0D), (Entity) this.getHandle());
            return this.toOffer != null;
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.offerTicks > 0;
    }

    @Override
    public void start() {
        this.offerTicks = 400;
        this.getHandle().a(true);
    }

    @Override
    public void finish() {
        this.getHandle().a(false);
        this.toOffer = null;
    }

    @Override
    public void tick() {
        this.getHandle().getControllerLook().a(this.toOffer, 30.0F, 30.0F);
        --this.offerTicks;
    }
}