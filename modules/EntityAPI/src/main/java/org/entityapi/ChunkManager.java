package org.entityapi;

import com.google.common.collect.Lists;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.EntityManager;

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