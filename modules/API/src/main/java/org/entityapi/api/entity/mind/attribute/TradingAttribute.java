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
import org.entityapi.api.entity.mind.Mind;

import java.util.ArrayList;
import java.util.List;

public class TradingAttribute extends Attribute {

    // TODO: Some neat stuff here...

    private String tradeName;
    private List<Player> tradingPlayers = new ArrayList<>();

    public TradingAttribute(Mind mind) {
        super(mind);
    }

    @Override
    public String getKey() {
        return "Trading";
    }

    public String getTradeName() {
        return tradeName;
    }

    public List<Player> getTradingPlayers() {
        return tradingPlayers;
    }
}