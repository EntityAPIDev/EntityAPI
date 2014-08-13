package org.entityapi.game;

public interface IEntityRegistry {

    public Class<?> registerEntity(int id, String name, Class<?> entity);

    public Class<?> getEntity(int id, String name);
}
