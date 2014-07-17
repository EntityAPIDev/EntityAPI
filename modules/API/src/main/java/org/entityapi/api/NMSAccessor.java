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

package org.entityapi.api;

import org.bukkit.Sound;
import org.bukkit.entity.LivingEntity;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntityHandle;

public interface NMSAccessor<T extends LivingEntity, S extends ControllableEntityHandle<T>> {

    Object getMonsterSelector();

    Object getSelector(SelectorType type);

    LivingEntity getBukkitEntity();

    String getSoundName(Sound sound);

    float getHeight();

    float getWidth();

    double getSpeed();

    void setSpeed(double speed);

    void setPathfindingRange(double range);

    double getPathfindingRange();

    boolean navigateTo(LivingEntity livingEntity, double speed);

    boolean navigateTo(Vector to, double speed);

    boolean navigateTo(Object path, double speed);

    boolean navigateTo(Object path);

    LivingEntity getTarget();

    void setTarget(LivingEntity target);

    void setYaw(float value);

    void setHeadYaw(float value);

    void setPitch(float value);

    float getFixedHeadYaw();

    boolean isAlive();

    void callBaseTick();

    void shootProjectile(ProjectileType projectileType, LivingEntity target, float strength);

    public enum SelectorType {
        CONTAINER, HORSE, LIVING, MONSTER, NOT_UNDEAD
    }
}