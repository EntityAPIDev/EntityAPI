package org.entityapi.game;

public interface EntityRegistry {

    public Class<?> registerEntity(int id, String name, Class<?> entity);

    public Class<?> getEntity(int id, String name);
}
