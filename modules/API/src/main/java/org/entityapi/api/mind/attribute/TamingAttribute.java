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

package org.entityapi.api.mind.attribute;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.entityapi.api.mind.Mind;

import java.util.UUID;

public class TamingAttribute extends Attribute {

    // TODO: What if EntityAPI is being run on a version that doesn't support UUID changes? e.g. 1.7.4
    private UUID tamerUuid;

    public TamingAttribute(Mind mind) {
        super(mind);
    }

    public TamingAttribute(Mind mind, UUID tamerUuid) {
        super(mind);
        this.tamerUuid = tamerUuid;
    }

    public TamingAttribute(Mind mind, Player player) {
        this(mind, player.getUniqueId());
    }

    @Override
    public String getKey() {
        return "Taming";
    }

    public boolean isTamed() {
        return this.tamerUuid != null;
    }

    public Player getTamer() {
        if (this.isTamed()) {
            return Bukkit.getPlayer(this.tamerUuid);
        }
        return null;
    }

    public void setTamer(UUID tamerUuid) {
        this.tamerUuid = tamerUuid;
    }

    public void setTamer(Player player) {
        this.setTamer(player.getUniqueId());
    }
}