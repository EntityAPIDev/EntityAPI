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

import org.bukkit.entity.Player;
import org.entityapi.api.entity.mind.Attribute;
import org.entityapi.api.events.Action;
import org.entityapi.api.events.ControllableEntityInteractEvent;

public abstract class InteractAttribute extends Attribute<ControllableEntityInteractEvent> {

    @Override
    protected ControllableEntityInteractEvent call(ControllableEntityInteractEvent event) {
        if (!event.isCancelled()) {
            event.setCancelled(!onInteract(event.getPlayer(), event.getAction()));
        }
        return event;
    }

    @Override
    protected ControllableEntityInteractEvent getNewEvent(Object... args) {
        return new ControllableEntityInteractEvent(getControllableEntity(), (Player) args[1], (Action) args[2]);
    }

    public abstract boolean onInteract(Player entity, Action rightClick);

    @Override
    public String getKey() {
        return "Interact";
    }
}