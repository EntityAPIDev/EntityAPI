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

package org.entityapi.api.entity.mind.attribute;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.entityapi.api.entity.mind.Attribute;
import org.entityapi.api.events.ControllableEntityCollideEvent;
import org.entityapi.api.events.ControllableEntityEvent;

public abstract class CollideAttribute extends Attribute<ControllableEntityCollideEvent> {

    @Override
    protected ControllableEntityCollideEvent getNewEvent(Object... args) {
        return new ControllableEntityCollideEvent(getControllableEntity(), (Entity) args[1]);
    }

    @Override
    protected ControllableEntityCollideEvent call(ControllableEntityCollideEvent event) {
        if (!event.isCancelled()) {
            onCollide(event.getCollidedWith());
        }
        return event;
    }

    public abstract void onCollide(Entity entity);

    @Override
    public String getKey() {
        return "Collide";
    }
}