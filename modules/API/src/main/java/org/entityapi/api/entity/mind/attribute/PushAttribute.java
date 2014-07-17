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

import org.bukkit.util.Vector;
import org.entityapi.api.entity.mind.Attribute;
import org.entityapi.api.events.ControllableEntityPushEvent;

public abstract class PushAttribute extends Attribute<ControllableEntityPushEvent> {

    @Override
    protected ControllableEntityPushEvent call(ControllableEntityPushEvent event) {
        if (event.isCancelled()) {
            event.setPushVelocity(new Vector(0, 0, 0));
        }
        onPush(event.getPushVelocity());
        return event;
    }

    @Override
    protected ControllableEntityPushEvent getNewEvent(Object... args) {
        return super.getNewEvent(args);
    }

    public abstract void onPush(Vector pushVelocity);

    @Override
    public String getKey() {
        return "Push";
    }
}