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
     * @return The ControllableEntity for this entity
     */
    ControllableEntity<T, ?> getControllableEntity();

    /**
     * Modifies the position of this entity
     *
     * @param x     X-coordinate of the new position
     * @param y     Y-coordinate of the new position
     * @param z     Z-coordinate of the new position
     * @param yaw   Yaw rotation of the new position
     * @param pitch Pitch rotation of the new position
     */
    void setPositionRotation(double x, double y, double z, float yaw, float pitch);
}