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

import org.entityapi.api.entity.mind.Attribute;
import org.entityapi.api.entity.mind.Mind;

public class TamedRidingAttribute extends Attribute {

    private int temper;
    private boolean rideable;

    public TamedRidingAttribute(TamedRidingAttribute attribute) {
        this.temper = attribute.temper;
        this.rideable = attribute.rideable;
    }

    @Override
    public String getKey() {
        return "Ride Tame";
    }

    public void increaseSuccessChance(int amount) {
        int temper = this.getTemper() + amount;
        this.temper = this.getTemper() + temper < 0 ? 0 : (temper > this.getMaxDomestication() ? this.getMaxDomestication() : temper);
    }

    public int getTemper() {
        return temper;
    }

    public int getMaxDomestication() {
        return 100;
    }

    public boolean isRideable() {
        return rideable;
    }

    @Override
    public TamedRidingAttribute copyTo(Mind mind) {
        return new TamedRidingAttribute(this);
    }
}