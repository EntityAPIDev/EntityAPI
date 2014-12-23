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

import net.minecraft.server.v1_8_R1.EntityAgeable;
import net.minecraft.server.v1_8_R1.EntityIronGolem;
import org.bukkit.entity.LivingEntity;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.behaviour.BehaviourType;
import org.entityapi.nms.v1_8_R1.NMSEntityUtil;
import org.entityapi.nms.v1_8_R1.entity.mind.behaviour.BehaviourGoalBase;

import java.util.Iterator;
import java.util.List;

public class BehaviourGoalTakeFlower extends BehaviourGoalBase {

    private EntityIronGolem flowerHolder;
    private int acceptTicks;
    private boolean canAccept;
    private double navigationSpeed;

    public BehaviourGoalTakeFlower(ControllableEntity controllableEntity, double navigationSpeed) {
        super(controllableEntity);
        this.navigationSpeed = navigationSpeed;
    }

    @Override
    public BehaviourType getType() {
        return BehaviourType.ACTION;
    }

    @Override
    public String getDefaultKey() {
        return "Take Flower";
    }

    @Override
    public boolean shouldStart() {
        if (this.getHandle() instanceof EntityAgeable && ((EntityAgeable) this.getHandle()).getAge() >= 0) {
            return false;
        } else if (!this.getHandle().world.v()) {
            return false;
        } else {
            List list = this.getHandle().world.a(EntityIronGolem.class, this.getHandle().boundingBox.grow(6.0D, 2.0D, 6.0D));

            if (list.isEmpty()) {
                return false;
            } else {
                Iterator iterator = list.iterator();

                while (iterator.hasNext()) {
                    EntityIronGolem candidate = (EntityIronGolem) iterator.next();

                    if (candidate.bZ() > 0) {
                        this.flowerHolder = candidate;
                        break;
                    }
                }

                return this.flowerHolder != null;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        return this.flowerHolder.bZ() > 0;
    }

    @Override
    public void start() {
        this.acceptTicks = this.getHandle().aI().nextInt(320);
        this.canAccept = false;
        this.flowerHolder.getNavigation().h();
    }

    @Override
    public void finish() {
        this.flowerHolder = null;
        NMSEntityUtil.getNavigation(this.getHandle()).h();
    }

    @Override
    public void tick() {
        NMSEntityUtil.getControllerLook(this.getHandle()).a(this.flowerHolder, 30.0F, 30.0F);
        if (this.flowerHolder.bZ() == this.acceptTicks) {
            this.getControllableEntity().navigateTo((LivingEntity) this.flowerHolder.getBukkitEntity(), this.navigationSpeed > 0 ? this.navigationSpeed : this.getControllableEntity().getSpeed());
            this.canAccept = true;
        }

        if (this.canAccept && this.getHandle().e(this.flowerHolder) < 4.0D) {
            this.flowerHolder.a(false);
            NMSEntityUtil.getNavigation(this.getHandle()).h();
        }
    }
}