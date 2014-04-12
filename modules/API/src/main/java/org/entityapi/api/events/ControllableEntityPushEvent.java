/*
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

package org.entityapi.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.util.Vector;
import org.entityapi.api.entity.ControllableEntity;

public class ControllableEntityPushEvent extends ControllableEntityEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();
    private boolean cancelled;

    private Vector pushVelocity;

    public ControllableEntityPushEvent(final ControllableEntity controllableEntity, final Vector pushVelocity) {
        super(controllableEntity);
        this.pushVelocity = pushVelocity;
    }

    /**
     * Gets the velocity entity was pushed at
     *
     * @return Vector representing pushed velocity
     */
    public Vector getPushVelocity() {
        return pushVelocity == null ? new Vector(0, 0, 0) : pushVelocity;
    }


    /**
     * Sets the velocity for the entity to be pushed
     *
     * @param pushVelocity new velocity to be pushed. This value cannot be null
     */
    public void setPushVelocity(Vector pushVelocity) {
        if (pushVelocity != null) {
            this.pushVelocity = pushVelocity;
        }
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}