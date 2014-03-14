package org.entityapi;

import org.bukkit.Location;
import org.entityapi.api.ControllableEntity;
import org.entityapi.api.ControllableEntityType;
import org.entityapi.api.mind.Mind;
import org.entityapi.api.mind.behaviour.Behaviour;
import org.entityapi.exceptions.ControllableEntitySpawnException;
import org.entityapi.reflection.SafeConstructor;

import java.util.HashMap;
import java.util.Map;

public class EntityCreator {
    private EntityCreator INSTANCE;

    private EntityManager ENTITYMANAGER;
    private int ID;
    private ControllableEntityType TYPE;
    private String NAME;
    private Location LOCATION;
    private boolean PREPARE;
    private Mind MIND;
    private HashMap<Behaviour, Integer> BEHAVIOURS;

    {
        this.BEHAVIOURS = new HashMap<>();
        this.PREPARE = false;
    }

    public EntityCreator(EntityManager entityManager) {
        this.ENTITYMANAGER = entityManager;
        this.INSTANCE = this;
    }

    public EntityCreator withID(int id) {
        this.ID = id;
        return this.INSTANCE;
    }

    public EntityCreator withType(ControllableEntityType entityType) {
        this.TYPE = entityType;
        return this.INSTANCE;
    }

    public EntityCreator withName(String name) {
        this.NAME = name;
        return this.INSTANCE;
    }

    public EntityCreator atLocation(Location location) {
        this.LOCATION = location;
        return this.INSTANCE;
    }

    public EntityCreator withMind(Mind mind) {
        this.MIND = mind;
        return this.INSTANCE;
    }

    public EntityCreator withBehaviours(Behaviour... behaviours) {
        for (Behaviour behaviour1 : behaviours) {
            this.BEHAVIOURS.put(behaviour1, 1);
        }
        return this.INSTANCE;
    }

    public EntityCreator withBehaviours(HashMap<Behaviour, Integer> prioritisedBehaviours) {
        for (Map.Entry<Behaviour, Integer> entry : prioritisedBehaviours.entrySet()) {
            this.BEHAVIOURS.put(entry.getKey(), entry.getValue());
        }
        return this.INSTANCE;
    }

    public EntityCreator withDefaults() {
        this.PREPARE = true;
        return this.INSTANCE;
    }

    public <T extends ControllableEntity> T create() {
        if (this.TYPE == null) {
            throw new NullPointerException("ControllableEntity Type cannot be null.");
        }
        if (this.LOCATION == null) {
            throw new NullPointerException("Location cannot be null.");
        }
        SafeConstructor<? extends ControllableEntity> constructor = new SafeConstructor<>(this.TYPE.getControllableClass());
        ControllableEntity entity = constructor.newInstance(this.ID, this.ENTITYMANAGER);
        if (entity != null) {
            if (entity.spawnEntity(this.LOCATION)) {
                if (this.PREPARE || this.MIND == null) {
                    this.MIND = new Mind();
                }
                this.MIND.setControllableEntity(entity);
                if (this.PREPARE) {
                    entity.setDefaultBehaviours();
                } else {
                    for (Map.Entry<Behaviour, Integer> entry : this.BEHAVIOURS.entrySet()) {
                        entity.getMind().getBehaviourSelector().addBehaviour(entry.getKey(), entry.getValue());
                    }
                }
                if (this.NAME != null) {
                    entity.setName(this.NAME);
                }
                try {
                    return (T) entity;
                } catch (ClassCastException e) {
                    throw new ControllableEntitySpawnException(e);
                }
            } else {
                throw new ControllableEntitySpawnException();
            }
        }
        return null;
    }
}
