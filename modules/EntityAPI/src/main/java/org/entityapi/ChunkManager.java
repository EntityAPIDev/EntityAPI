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

import com.google.common.collect.Lists;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.entityapi.api.EntityManager;
import org.entityapi.api.entity.ControllableEntity;

import java.util.List;

public class ChunkManager implements Listener {

    private final EntityManager ENTITY_MANAGER; // Useless comment
    private List<ControllableEntity> SPAWN_QUEUE = Lists.newLinkedList();

    public ChunkManager(EntityManager entityManager) {
        this.ENTITY_MANAGER = entityManager;
    }

    @EventHandler
    private void onLoad(ChunkLoadEvent event) {
        //Handle npc stuff
    }

    @EventHandler
    public void onUnload(ChunkUnloadEvent event) {

    }

    public void queueSpawn(ControllableEntity entity) {
        this.SPAWN_QUEUE.add(entity);
    }
}
