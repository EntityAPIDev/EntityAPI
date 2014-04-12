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

package org.entityapi;

import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import org.entityapi.api.entity.ControllableEntity;
import org.entityapi.api.entity.ControllableEntityType;
import org.entityapi.api.entity.DespawnReason;
import org.entityapi.api.EntityManager;
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

        this.CHUNK_MANAGER = new ChunkManager(this);

        Bukkit.getPluginManager().registerEvents(this.CHUNK_MANAGER, EntityAPI.getCore());

        this.TASK_ID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            @Override
            public void run() {
                Iterator<Map.Entry<Integer, ControllableEntity>> iterator = ENTITIES.entrySet().iterator();

                while (iterator.hasNext()) {
                    Map.Entry<Integer, ControllableEntity> entry = iterator.next();

                    if (EntityAPI.getBasicEntityUtil().getHandle(entry.getValue()) == null) {
                        if (!keepEntitiesInMemory)
                            iterator.remove();
                    } else {
                        EntityAPI.getBasicEntityUtil().callBaseTick(entry.getValue());
                        if (!EntityAPI.getBasicEntityUtil().isAlive(entry.getValue())) {
                            //TODO: despawn
                        }
                    }
                }
            }
        }, 1L, 1L);
    }

    public Plugin getOwningPlugin() {
        return this.OWNING_PLUGIN;
    }

    public boolean isKeepEntitiesInMemory() {
        return this.KEEP_ENTITIES_IN_MEM;
    }

    public void setKeepEntitiesInMemory(boolean bool) {
        this.KEEP_ENTITIES_IN_MEM = bool;
    }

    protected Integer getNextID() {
        return getNextID(Integer.MIN_VALUE);
    }

    protected Integer getNextID(int index) {
        Set<Integer> ids = this.ENTITIES.keySet();
        while (ids.contains(index)) {
            index++;
        }
        return index;
    }

    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location) {
        return spawnEntity(entityType, location, true);
    }

    public ControllableEntity spawnEntity(ControllableEntityType entityType, Location location, boolean prepare) {
        try {
            if (entityType.isNameRequired())
                throw new NameRequiredException();

            Integer id = getNextID();

            EntityCreator context = new EntityCreator(this);

            context.withID(id)
                    .withType(entityType)
                    .atLocation(location);

            if (prepare)
                context.withDefaults();

            return context.create();
        } catch (Throwable throwable) {
            EntityAPI.LOGGER.warning("Failed to create an Entity handle for type: " + entityType.getName());
            throwable.printStackTrace();
            return null;
        }
    }

    public Collection<ControllableEntity> getEntities() {
        return Collections.unmodifiableCollection(this.ENTITIES.values());
    }

    public void despawnAll() {

    }

    public void despawnAll(DespawnReason despawnReason) {

    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(128);
        builder.append("EntityManager{plugin=" + this.OWNING_PLUGIN.getName() + ",")
                .append("entities-spawned=" + this.ENTITIES.size() + "}");
        return builder.toString();
    }

    @Override
    public int hashCode() {
        return super.hashCode() ^ this.toString().hashCode();
    }
}
