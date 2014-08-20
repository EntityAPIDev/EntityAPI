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

package org.entityapi.api.entity.mind;

import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.events.ControllableEntityEvent;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.exceptions.AttributeMindRequiredException;

public abstract class Attribute<T extends ControllableEntityEvent> {

    protected Mind mind;

    public T call(Object... args) {
        T event = null;
        try {
            event = getNewEvent(args);
        } catch (ArrayIndexOutOfBoundsException ignored) {

        }

        EntityAPI.getCore().getServer().getPluginManager().callEvent(event);

        return call(event);
    }

    protected T call(T event) {
        return event;
    }

    protected T getNewEvent(Object... args) {
        return null;
    }

    protected Attribute applyTo(Mind mind) {
        Attribute attribute = this.copyTo(mind);
        if (attribute == null) {
            try {
                attribute = this.getClass().newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Failed to copyTo new Attribute! Ensure that the Attribute#copyTo(Mind) method constructs a new Attribute that can be applied to an entity's mind.", e);
            }
        }
        if (attribute != null) {
            attribute.mind = mind;
        }
        return attribute;
    }

    public abstract Attribute copyTo(Mind mind);

    protected void removeFrom(Mind mind) {
        this.mind = null;
        this.onRemove(mind);
    }

    protected void onRemove(Mind mind) {
    }

    public Mind getMind() {
        if (mind == null) {
            throw new AttributeMindRequiredException();
        }
        return mind;
    }

    public ControllableEntity getControllableEntity() {
        return this.mind.getControllableEntity();
    }

    public abstract String getKey();

    public void tick() {

    }
}