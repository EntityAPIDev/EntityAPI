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

package org.entityapi.nms.v1_7_R1.entity;

import net.minecraft.server.v1_7_R1.*;
import net.minecraft.util.com.mojang.authlib.GameProfile;
import org.entityapi.api.entity.type.ControllablePlayer;
import org.entityapi.api.entity.type.nms.ControllablePlayerHandle;
import org.entityapi.nms.v1_7_R1.network.FixedNetworkManager;
import org.entityapi.nms.v1_7_R1.network.NullPlayerConnection;
import org.entityapi.nms.v1_7_R1.player.PlayerControllerJump;
import org.entityapi.nms.v1_7_R1.player.PlayerControllerLook;
import org.entityapi.nms.v1_7_R1.player.PlayerControllerMove;
import org.entityapi.nms.v1_7_R1.player.PlayerNavigation;

public class ControllablePlayerEntity extends EntityPlayer implements ControllablePlayerHandle {

    protected ControllablePlayer controllablePlayer;

    protected Navigation navigation;
    protected ControllerMove moveController;
    protected ControllerLook lookController;
    protected ControllerJump jumpController;

    public ControllablePlayerEntity(MinecraftServer minecraftServer, WorldServer worldServer, GameProfile gameProfile, PlayerInteractManager playerInteractManager, ControllablePlayer controllablePlayer) {
        this(minecraftServer, worldServer, gameProfile, playerInteractManager);
        this.controllablePlayer = controllablePlayer;
    }

    public ControllablePlayerEntity(MinecraftServer minecraftserver, WorldServer worldserver, GameProfile gameprofile, PlayerInteractManager playerinteractmanager) {
        super(minecraftserver, worldserver, gameprofile, playerinteractmanager);

        NetworkManager manager = new FixedNetworkManager();
        playerConnection = new NullPlayerConnection(server, manager, this);
        manager.a(playerConnection);

        noDamageTicks = 1;

        this.navigation = new PlayerNavigation(this, this.world);
        this.moveController = new PlayerControllerMove(this);
        this.lookController = new PlayerControllerLook(this);
        this.jumpController = new PlayerControllerJump(this);
    }

    @Override
    public ControllablePlayer getControllableEntity() {
        return this.controllablePlayer;
    }

    @Override
    public org.bukkit.Material getDefaultMaterialLoot() {
        return null;
    }

    public void updateControllers() {
        getControllerLook().a();
        getControllerJump().b();
        getControllerMove().c();
        getNavigation().f();
    }

    public Navigation getNavigation() {
        return this.navigation;
    }

    public ControllerMove getControllerMove() {
        return this.moveController;
    }

    public ControllerLook getControllerLook() {
        return this.lookController;
    }

    public ControllerJump getControllerJump() {
        return this.jumpController;
    }
}
