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

package org.entityapi.nms.v1_7_R1.entity;

import net.minecraft.server.v1_7_R1.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityHandle;
import org.entityapi.nms.v1_7_R1.PlayerNavigation;
import org.entityapi.nms.v1_7_R1.network.FixedNetworkManager;
import org.entityapi.nms.v1_7_R1.network.NullPlayerConnection;

public class ControllablePlayerEntity extends EntityPlayer implements ControllableEntityHandle {

    private Navigation navigation;

    public ControllablePlayerEntity(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);

        NetworkManager manager = new FixedNetworkManager();
        playerConnection = new NullPlayerConnection(server, manager, this);
        manager.a(playerConnection);

        noDamageTicks = 1;

        this.navigation = new PlayerNavigation(this);
    }

    @Override
    public ControllableEntity getControllableEntity() {
        return null;
    }

    @Override
    public org.bukkit.Material getDefaultMaterialLoot() {
        return null;
    }

    public Navigation getNavigation() {
        return this.navigation;
    }
}
