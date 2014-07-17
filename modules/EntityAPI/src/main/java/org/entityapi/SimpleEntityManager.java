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

import com.dsh105.commodus.GeneralUtil;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.entityapi.api.ChunkManager;
import org.entityapi.api.EntityBuilder;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.entity.mind.attribute.DespawnAttribute;
import org.entityapi.api.events.ControllableEntityDeathEvent;
import org.entityapi.api.events.ControllableEntityDespawnEvent;
import org.entityapi.api.plugin.EntityAPI;
import org.entityapi.exceptions.NameRequiredException;

import java.util.*;

public class SimpleEntityManager implements EntityManager {

    private final Plugin OWNING_PLUGIN;
    private boolean KEEP_ENTITIES_IN_MEM;

    private final Map<Integer, ControllableEntity> ENTITIES = Maps.newConcurrentMap();

    private final ChunkManager CHUNK_MANAGER;
    private final int TASK_ID;

    public SimpleEntityManager(Plugin plugin, final boolean keepEntitiesInMemory) {
        this.OWNING_PLUGIN = plugin;
        this.KEEP_ENTITIES_IN_MEM = keepEntitiesInMemory;

        this.CHUNK_MANAGER = new SimpleChunkManager(this);

        Bukkit.getPluginManager().registerEvents(this.CHUNK_MANAGER, EntityAPI.getCore());

        this.TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<Integer, ControllableEntity>> iterator = ENTITIES.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Integer, ControllableEntity> entry = iterator.next();

                    if (entry.getValue().getHandle() == null) {
                        if (!willKeepEntitiesInMemory()) {
                            iterator.remove();
                        }
                    } else {
                        if (!entry.getValue().getNMSAccessor().isAlive()) {
                            despawn(entry.getValue());
                        }
                    }
                }
            }
        }, 1L, 1L);
    }

    @Override
    public Collection<ControllableEntity> getEntities() {
        return Collections.unmodifiableCollection(this.ENTITIES.values());
    }

    @Override
    public ChunkManager getChunkManager() {
        return CHUNK_MANAGER;
    }

    @Override
    public Plugin getOwningPlugin() {
        return this.OWNING_PLUGIN;
    }

    @Override
    public boolean willKeepEntitiesInMemory() {
        return this.KEEP_ENTITIES_IN_MEM;
    }

    @Override
    public void setKeepEntitiesInMemory(boolean bool) {
        this.KEEP_ENTITIES_IN_MEM = bool;
    }

    @Override
    public Integer getNextID() {
        return getNextID(Integer.MIN_VALUE);
    }

    @Override
    public Integer getNextID(int index) {
        Set<Integer> ids = this.ENTITIES.keySet();
        Collections.addAll(ids, getChunkManager().getQueuedEntityIds().toArray(new Integer[0]));
        while (ids.contains(index)) {
            index++;
        }
        return index;
    }

    @Override
    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location) {
        return spawnEntity(entityType, location, true);
    }

    @Override
    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location, boolean prepare) {
        try {
            if (entityType.isNameRequired()) {
                throw new NameRequiredException();
            }

            EntityBuilder context = new EntityBuilder(this);

            context.withType(entityType).atLocation(location);

            if (prepare) {
                context.withDefaults();
            }

            return context.create();
        } catch (Throwable throwable) {
            throw new RuntimeException("Failed to create an Entity handle for type: " + entityType.getName(), throwable);
        }
    }

    @Override
    public boolean spawn(ControllableEntity controllableEntity, Location location) {
        return controllableEntity.spawn(location);
    }

    @Override
    public void despawnAll() {
        despawnAll(DespawnReason.DEATH);
    }

    @Override
    public void despawnAll(DespawnReason reason) {
        for (ControllableEntity controllableEntity : getEntities()) {
            despawn(controllableEntity, reason);
        }
    }

    @Override
    public void despawn(ControllableEntity controllableEntity) {
        despawn(controllableEntity, DespawnReason.DEATH);
    }

    @Override
    public void despawn(ControllableEntity controllableEntity, DespawnReason reason) {
        if (controllableEntity.isSpawned()) {
            controllableEntity.despawn(reason);
        }
        if (!controllableEntity.isSpawned()) {
            Integer id = GeneralUtil.getKeyAtValue(ENTITIES, controllableEntity);
            if (id != null) { // May have already been removed
                this.ENTITIES.remove(id);
            }
        }
    }

    @Override
    public String toString() {
        return "EntityManager{plugin=" + this.OWNING_PLUGIN.getName() + "," + "entities-spawned=" + this.ENTITIES.size() + "}";
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.toString().hashCode();
    }
}
