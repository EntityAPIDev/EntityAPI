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
import org.entityapi.api.entity.mind.Mind;
import org.entityapi.api.utils.IdentUtils;

public class TamingAttribute extends Attribute {
    private String tamerIdentification;

    public TamingAttribute(Player player) {
        this(IdentUtils.getIdentificationForAsString(player));
    }

    private TamingAttribute(String tamerIdentification) {
        this.tamerIdentification = tamerIdentification;
    }

    @Override
    public String getKey() {
        return "Taming";
    }

    public boolean isTamed() {
        return this.tamerIdentification != null;
    }

    public Player getTamer() {
        if (this.isTamed()) {
            return IdentUtils.getPlayerOf(tamerIdentification);
        }
        return null;
    }

    public void setTamer(Player player) {
        this.setTamer(IdentUtils.getIdentificationForAsString(player));
    }

    private void setTamer(String tamerIdentification) {
        this.tamerIdentification = tamerIdentification;
    }

    @Override
    public TamingAttribute copyTo(Mind mind) {
        return new TamingAttribute(this.tamerIdentification);
    }
}