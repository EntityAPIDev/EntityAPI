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

/**
 * Represents an entity that can be named
 * <p>
 * Name visibility will be modified according to whether the entity currently has a name set
 */
public interface Nameable {

    /**
     * Gets the name currently applied to this entity
     *
     * @return the name applied to this entity
     */
    public abstract String getName();

    /**
     * Sets the name of this entity
     * <p>
     * Setting to {@code null} will remove cause the name tag to be removed.
     * <p>
     * Note that {@link org.bukkit.entity.LivingEntity#setCustomName(String)} can also be used on a ControllableEntity
     *
     * @param name the new name to apply to this entity
     * @return true if the name was successfully applied, false if not
     */
    public abstract boolean setName(String name);
}