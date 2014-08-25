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

package org.entityapi;

import com.captainbern.minecraft.conversion.BukkitUnwrapper;
import com.google.common.collect.Lists;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.entityapi.api.ChunkManager;
import org.entityapi.api.EntityChunkData;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityHandle;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.entity.SpawnResult;
import org.entityapi.exceptions.ControllableEntitySpawnException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SimpleChunkManager implements ChunkManager {

    private final EntityManager ENTITY_MANAGER; // Useless comment
    private List<EntityChunkData> SPAWN_QUEUE = Lists.newLinkedList();

    public SimpleChunkManager(EntityManager entityManager) {
        this.ENTITY_MANAGER = entityManager;
    }

    @EventHandler
    private void onLoad(ChunkLoadEvent event) {
        Chunk loadedChunk = event.getChunk();
        for (EntityChunkData entityChunkData : SPAWN_QUEUE) {
            if (loadedChunk == entityChunkData.getRespawnLocation().getChunk()) {
                if (entityChunkData.getControllableEntity().spawn(entityChunkData.getRespawnLocation()) == SpawnResult.FAILED) {
                    throw new ControllableEntitySpawnException();
                }
            }
        }
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent event) {
        Chunk unloadedChunk = event.getChunk();
        for (Entity entity : unloadedChunk.getEntities()) {
            if (entity instanceof LivingEntity) {
                Object handle = BukkitUnwrapper.getInstance().unwrap(entity);
                if (handle instanceof ControllableEntityHandle) {
                    ControllableEntity controllableEntity = ((ControllableEntityHandle) handle).getControllableEntity();
                    if (controllableEntity != null && controllableEntity.isSpawned()) {
                        this.SPAWN_QUEUE.add(new EntityChunkData(controllableEntity, entity.getLocation()));
                        controllableEntity.despawn(DespawnReason.CHUNK_UNLOAD);
                    }
                }
            }
        }
    }

    @Override
    public void queueSpawn(ControllableEntity entity, Location location) {
        if (canSpawn(location)) {
            entity.spawn(location);
            return;
        }

        this.SPAWN_QUEUE.add(new EntityChunkData(entity, location));
    }

    @Override
    public Collection<ControllableEntity> getQueuedEntities() {
        List<ControllableEntity> controllableEntities = new ArrayList<>();
        for (EntityChunkData entityChunkData : SPAWN_QUEUE) {
            controllableEntities.add(entityChunkData.getControllableEntity());
        }
        return Collections.unmodifiableCollection(controllableEntities);
    }

    @Override
    public Collection<Integer> getQueuedEntityIds() {
        List<Integer> entityIds = new ArrayList<>();
        for (ControllableEntity controllableEntity : getQueuedEntities()) {
            entityIds.add(controllableEntity.getId());
        }
        return Collections.unmodifiableCollection(entityIds);
    }

    @Override
    public boolean canSpawn(Location location) {
        return canSpawn(location.getChunk());
    }

    @Override
    public boolean canSpawn(Chunk chunk) {
        return chunk.isLoaded();
    }
}
