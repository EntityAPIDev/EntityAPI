package io.snw.entityapi;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkLoadEvent;

public class ChunkManager implements Listener {

    private final EntityManager ENTITY_MANAGER;

    public ChunkManager(EntityManager entityManager) {
        this.ENTITY_MANAGER = entityManager;
    }

    @EventHandler
    private void onLoad(ChunkLoadEvent event) {
        //Handle npc stuff
    }
}
