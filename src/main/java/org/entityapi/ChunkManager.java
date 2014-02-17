package org.entityapi;

import com.google.common.collect.Lists;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.entityapi.api.ControllableEntity;

import java.util.Queue;

public class ChunkManager implements Listener {

    private final EntityManager ENTITY_MANAGER;
    private Queue SPAWN_QUEUE = Lists.newLinkedList();

    public ChunkManager(EntityManager entityManager) {
        this.ENTITY_MANAGER = entityManager;
    }

    @EventHandler
    private void onLoad(ChunkLoadEvent event) {
        //Handle npc stuff
    }

    public void queueSpawn(ControllableEntity entity) {
        this.SPAWN_QUEUE.add(entity);
    }
}
