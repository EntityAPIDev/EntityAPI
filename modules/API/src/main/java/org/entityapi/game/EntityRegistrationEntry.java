package org.entityapi.game;

public class EntityRegistrationEntry {

    private final String name;
    private final int id;
    private final Class<?> entityClass;

    public EntityRegistrationEntry(String name, int id, Class<?> entityClass) {
        this.name = name;
        this.id = id;
        this.entityClass = entityClass;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public Class<?> getEntityClass() {
        return this.entityClass;
    }
}
