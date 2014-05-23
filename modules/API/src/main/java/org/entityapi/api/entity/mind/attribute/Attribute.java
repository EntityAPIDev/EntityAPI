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

import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.exceptions.AttributeAlreadyInUseException;
import org.entityapi.exceptions.AttributeMindRequiredException;

public abstract class Attribute {

    protected Mind mind;

    public Attribute() {
    }

    public void setMind(Mind mind) {
        if (this.mind != null && mind != null) {
            throw new AttributeAlreadyInUseException();
        }
        this.mind = mind;
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