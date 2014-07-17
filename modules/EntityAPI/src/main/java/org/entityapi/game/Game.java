package org.entityapi.game;

public class Game {
    
    private static EntityRegistry entityRegistry;

    public static EntityRegistry getEntityRegistry() {
        if (entityRegistry == null)
            throw new RuntimeException("EntityRegistry not set!");

        return entityRegistry;
    }

    public static void setEntityRegistry(EntityRegistry registry) {
        if (entityRegistry != null)
            throw new RuntimeException("EntityRegistry already set!");

        entityRegistry = registry;
    }
}
