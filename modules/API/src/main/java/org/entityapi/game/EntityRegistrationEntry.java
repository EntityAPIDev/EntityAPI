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

package org.entityapi.game;

import org.entityapi.api.entity.ControllableEntityType;

public class EntityRegistrationEntry {

    private final String name;
    private final int id;
    private final Class<?> entityClass;

    public EntityRegistrationEntry(String name, int id, Class<?> entityClass) {
        this.name = name;
        this.id = id;
        this.entityClass = entityClass;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Class<?> getEntityClass() {
        return this.entityClass;
    }

    public static EntityRegistrationEntry createFor(ControllableEntityType entityType) {
        return new EntityRegistrationEntry(entityType.getName(), entityType.getId(), entityType.getHandleClass());
    }
}
