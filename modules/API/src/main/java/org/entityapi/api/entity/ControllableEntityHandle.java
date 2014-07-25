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

package org.entityapi.api.entity;

import org.bukkit.Material;
import org.bukkit.entity.LivingEntity;

/**
 * Represents the NMS handle for a {@link org.entityapi.api.entity.ControllableEntity}
 *
 * @param <T> Bukkit entity represented by this ControllableEntity handle
 */
public interface ControllableEntityHandle<T extends LivingEntity> {

    /**
     * Gets the ControllableEntity that this handle is representing
     *
     * @return the ControllableEntity for this entity
     */
    ControllableEntity<T, ?> getControllableEntity();

    /**
     * Gets the default loot dropped when this entity is killed
     * <p/>
     * <strong>Not intended for public API consumption</strong>
     *
     * @return the default loot for this entity
     */
    Material getDefaultLoot();

    /**
     * Modifies the position of this entity
     *
     * @param x     x-coordinate of the new position
     * @param y     y-coordinate of the new position
     * @param z     z-coordinate of the new position
     * @param yaw   yaw rotation of the new position
     * @param pitch pitch rotation of the new position
     */
    void setPositionRotation(double x, double y, double z, float yaw, float pitch);
}