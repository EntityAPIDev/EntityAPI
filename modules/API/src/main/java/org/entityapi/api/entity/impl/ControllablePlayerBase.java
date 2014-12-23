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

package org.entityapi.api.entity.impl;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.SpawnResult;
import org.entityapi.api.entity.type.ControllablePlayer;
import org.entityapi.api.entity.type.nms.ControllablePlayerHandle;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.game.GameRegistry;
import org.entityapi.game.IEntitySpawnHandler;

import java.util.UUID;

public class ControllablePlayerBase extends ControllableBaseEntity<Player, ControllablePlayerHandle> implements ControllablePlayer {

    protected String name;

    public ControllablePlayerBase(int id, String name, EntityManager manager) {
        this(id, name, null, manager);
    }

    public ControllablePlayerBase(int id, String name, ControllablePlayerHandle entityHandle, EntityManager manager) {
        super(id, ControllableEntityType.HUMAN, manager);
        this.name = name;

        if (entityHandle != null) {
            this.handle = entityHandle;
        }
    }

    @Override
    public SpawnResult spawn(Location location) {
        if (isSpawned()) {
            return SpawnResult.ALREADY_SPAWNED;
        }

        this.handle = GameRegistry.get(IEntitySpawnHandler.class).createPlayerHandle(this, location, this.name, UUID.nameUUIDFromBytes(("EntityAPI-NPC:" + this.getId() + this.name).getBytes()));

        Player entity = this.getBukkitEntity();
        if (entity != null) {
            entity.setMetadata(EntityAPI.ENTITY_METADATA_MARKER, new FixedMetadataValue(EntityAPI.getCore(), true)); // Perhaps make this a key somewhere
            entity.teleport(location);
            entity.setSleepingIgnored(true);
        }

        // Send the Packet
        handle.updateSpawn();

        //return spawned;
        return isSpawned() ? SpawnResult.SUCCESS : SpawnResult.FAILED;
    }
}
